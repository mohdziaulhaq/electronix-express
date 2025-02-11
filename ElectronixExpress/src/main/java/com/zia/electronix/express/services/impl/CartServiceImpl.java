package com.zia.electronix.express.services.impl;

import com.zia.electronix.express.dtos.AddItemToCartRequest;
import com.zia.electronix.express.dtos.CartDto;
import com.zia.electronix.express.entities.Cart;
import com.zia.electronix.express.entities.CartItem;
import com.zia.electronix.express.entities.Product;
import com.zia.electronix.express.entities.User;
import com.zia.electronix.express.exception.BadApiRequestException;
import com.zia.electronix.express.exception.ResourceNotFoundException;
import com.zia.electronix.express.repositories.CartItemRepository;
import com.zia.electronix.express.repositories.CartRepository;
import com.zia.electronix.express.repositories.ProductRepository;
import com.zia.electronix.express.repositories.UserRepository;
import com.zia.electronix.express.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity<=0)
            throw new BadApiRequestException("Quantity should be greater than 0");
        //fetch product from its id
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // fetch user from its id
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Cart cart = null;

        try{
            cart = cartRepository.findByUser(user).get();
        }
        catch (NoSuchElementException e){
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
            cart.setUser(user);
        }

        AtomicBoolean updated = new AtomicBoolean(false);
        List<CartItem> items = cart.getItems();

        // if cartItem already presnet then just update quantity
        List<CartItem> updatedItems = items.stream().map(item ->{
            if(item.getProduct().getProductId().equals(productId)){
                //item present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(item.getProduct().getDiscountedPrice() * quantity);
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());
//        cart.setItems(updatedItems);

        cart.getItems().clear();  // Remove existing items
        cart.getItems().addAll(updatedItems);  // Add new items

        if(!updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice( (quantity * product.getDiscountedPrice()))
                    .cart(cart)
                    .product(product).build();
            cart.getItems().add(cartItem);
        }
        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CartItem cartItem =  cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found"));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return modelMapper.map(cart, CartDto.class);
    }
}
