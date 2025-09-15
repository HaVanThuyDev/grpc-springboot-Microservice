package com.grpc.accountservicegrpc.Config;

import com.grpc.accountservicegrpc.Dto.ProductDto;
import com.grpc.accountservicegrpc.Service.ProductService;
import com.google.protobuf.Empty;
import com.service.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {
    @Autowired
    private ProductService productService;

    @Override
    public void getProduct(ProductRequest request,
                           StreamObserver<Product> responseObserver) {
        ProductDto dto = productService.getProductById(request.getId());
        if (dto == null) {
            responseObserver.onError(new RuntimeException("Product not found with id: " + request.getId()));
            return;
        }
        responseObserver.onNext(mapToProto(dto));
        responseObserver.onCompleted();
    }
    @Override
    public void getAllProducts(Empty request,
                               StreamObserver<ProductList> responseObserver) {
        List<ProductDto> products = productService.getAllProducts();
        ProductList list = ProductList.newBuilder()
                .addAllProducts(products.stream().map(this::mapToProto).collect(Collectors.toList()))
                .build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }
    @Override
    public void createProduct(CreateProductRequest request,
                              StreamObserver<Product> responseObserver) {
        ProductDto dto = new ProductDto(
                null,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getBrand(),
                request.getCategoryId()
        );
        ProductDto created = productService.createProduct(dto);
        responseObserver.onNext(mapToProto(created));
        responseObserver.onCompleted();
    }

    @Override
    public void updateProduct(UpdateProductRequest request,
                              StreamObserver<Product> responseObserver) {
        ProductDto dto = new ProductDto(
                request.getId(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getBrand(),
                request.getCategoryId()
        );
        ProductDto updated = productService.updateProduct(request.getId(), dto);
        if (updated == null) {
            responseObserver.onError(new RuntimeException("Product not found with id: " + request.getId()));
            return;
        }
        responseObserver.onNext(mapToProto(updated));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteProduct(DeleteProductRequest request,
                              StreamObserver<Empty> responseObserver) {
        productService.deleteProduct(request.getId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
    @Override
    public void searchProducts(SearchRequest request,
                               StreamObserver<ProductList> responseObserver) {
        List<ProductDto> products = productService.searchByName(request.getKeyword());
        ProductList list = ProductList.newBuilder()
                .addAllProducts(products.stream().map(this::mapToProto).collect(Collectors.toList()))
                .build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }
    private Product mapToProto(ProductDto dto) {
        return Product.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setPrice(dto.getPrice())
                .setBrand(dto.getBrand())
                .setCategoryId(dto.getCategoryId())
                .build();
    }
}
