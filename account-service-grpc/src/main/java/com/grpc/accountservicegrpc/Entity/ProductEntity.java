package com.grpc.accountservicegrpc.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id tự tăng
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Float price;

    private String brand;

    @Column(name = "category_id")
    private String categoryId;
}
