package com.grpc.clientservice.service;

import com.google.protobuf.Empty;
import com.grpc.clientservice.dto.ProductDto; // Import DTO của client
import com.service.grpc.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrpcProductClientService {

    private final ProductServiceGrpc.ProductServiceBlockingStub productStub;
    private ProductDto mapToDto(Product grpcProduct) {
        return ProductDto.builder()
                .id(grpcProduct.getId())
                .name(grpcProduct.getName())
                .description(grpcProduct.getDescription())
                .price(grpcProduct.getPrice())
                .brand(grpcProduct.getBrand())
                .categoryId(grpcProduct.getCategoryId())
                .build();
    }
    public List<ProductDto> getAllProducts() {
        ProductList grpcProductList = productStub.getAllProducts(Empty.getDefaultInstance());
        return grpcProductList.getProductsList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    public ProductDto getProductById(int id) {
        ProductRequest request = ProductRequest.newBuilder()
                .setId(id)
                .build();
        Product grpcProduct = productStub.getProduct(request);
        return mapToDto(grpcProduct);
    }
    public ProductDto createProduct(String name, String description, float price, String brand, String categoryId) {
        CreateProductRequest request = CreateProductRequest.newBuilder()
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setBrand(brand)
                .setCategoryId(categoryId)
                .build();
        Product grpcProduct = productStub.createProduct(request);
        return mapToDto(grpcProduct);
    }
    public ProductDto updateProduct(int id, String name, String description, float price, String brand, String categoryId) {
        UpdateProductRequest request = UpdateProductRequest.newBuilder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setBrand(brand)
                .setCategoryId(categoryId)
                .build();
        Product grpcProduct = productStub.updateProduct(request);
        return mapToDto(grpcProduct);
    }
    public void deleteProduct(int id) {
        DeleteProductRequest request = DeleteProductRequest.newBuilder()
                .setId(id)
                .build();
        productStub.deleteProduct(request);
    }
    public List<ProductDto> searchProducts(String keyword) {
        SearchRequest request = SearchRequest.newBuilder()
                .setKeyword(keyword)
                .build();
        ProductList grpcProductList = productStub.searchProducts(request);
        return grpcProductList.getProductsList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}