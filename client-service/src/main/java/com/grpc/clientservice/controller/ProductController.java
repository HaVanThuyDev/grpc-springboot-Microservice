package com.grpc.clientservice.controller;

import com.grpc.clientservice.dto.ProductDto;
import com.grpc.clientservice.service.GrpcProductClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final GrpcProductClientService productClientService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productClientService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int id) {
        ProductDto product = productClientService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productClientService.createProduct(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getBrand(),
                productDto.getCategoryId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productClientService.updateProduct(
                id,
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getBrand(),
                productDto.getCategoryId()
        );
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productClientService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String keyword) {
        List<ProductDto> products = productClientService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }
}