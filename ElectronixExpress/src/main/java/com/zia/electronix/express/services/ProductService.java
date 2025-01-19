package com.zia.electronix.express.services;

import com.zia.electronix.express.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    //create
    ProductDto createProduct(ProductDto productDto);
    //update
    ProductDto updateProduct(ProductDto productDto, String productId);
    //delete
    void deleteProduct(String productId);
    //get single
    ProductDto getProduct(String productId);
    //get all
    List<ProductDto> getAllProducts();
    // get all : live
    List<ProductDto> getAllLiveProducts();
    //search
    List<ProductDto> searchProducts(String subTitle);
}
