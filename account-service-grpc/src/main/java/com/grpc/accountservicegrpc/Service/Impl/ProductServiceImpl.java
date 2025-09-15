package com.grpc.accountservicegrpc.Service.Impl;

import com.grpc.accountservicegrpc.Dto.ProductDto;
import com.grpc.accountservicegrpc.Entity.ProductEntity;
import com.grpc.accountservicegrpc.Repository.ProductRepository;
import com.grpc.accountservicegrpc.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto getProductById(Integer id) {
        ProductEntity entity = productRepository.findById(id).orElse(null);
        if (entity == null) return null;
        return mapToDto(entity);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        ProductEntity entity = mapToEntity(dto);
        ProductEntity saved = productRepository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto dto) {
        ProductEntity entity = productRepository.findById(id).orElse(null);
        if (entity == null) return null;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setBrand(dto.getBrand());
        entity.setCategoryId(dto.getCategoryId());

        ProductEntity updated = productRepository.save(entity);
        return mapToDto(updated);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ProductDto mapToDto(ProductEntity entity) {
        return new ProductDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getBrand(),
                entity.getCategoryId()
        );
    }

    private ProductEntity mapToEntity(ProductDto dto) {
        return new ProductEntity(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getBrand(),
                dto.getCategoryId()
        );
    }
}
