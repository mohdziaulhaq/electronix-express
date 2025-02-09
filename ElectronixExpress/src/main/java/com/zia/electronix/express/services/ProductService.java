package com.zia.electronix.express.services;

import com.zia.electronix.express.dtos.PageableResponse;
import com.zia.electronix.express.dtos.ProductDto;
import org.hibernate.query.Page;

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
    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);
    // get all : live
    PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy, String sortDir);
    //search
    PageableResponse<ProductDto> searchProducts(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir);

    //create product with category
    ProductDto createProductWithCategory(ProductDto productDto, String categoryId);

    ProductDto updateCategory(String productId, String categoryId);

    PageableResponse<ProductDto> getAllProductsWithCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);
}
