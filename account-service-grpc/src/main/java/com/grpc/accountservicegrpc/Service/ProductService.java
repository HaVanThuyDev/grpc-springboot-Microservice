package com.grpc.accountservicegrpc.Service;

import com.grpc.accountservicegrpc.Dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto getProductById(Integer id);
    List<ProductDto> getAllProducts();
    ProductDto createProduct(ProductDto dto);
    ProductDto updateProduct(Integer id, ProductDto dto);
    void deleteProduct(Integer id);
    List<ProductDto> searchByName(String keyword);
}
