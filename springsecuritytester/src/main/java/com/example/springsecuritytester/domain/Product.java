package com.example.springsecuritytester.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
Why @Builder ?
The @Builder annotation belongs to the Project Lombok library
and it provides a way to automate the generation of builder-style methods for your class.

Without @Builder annotation
----------------------------------------------------------------------
Product product = new Product();
product.setProductId(1);
product.setName("Product Name");
product.setQty(5);
product.setPrice(100.0);

With @Builder annotation
---------------------------------------------
Product product = Product.builder()
    .productId(1)
    .name("Product Name")
    .qty(5)
    .price(100.0)
    .build();
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String name;
    private int qty;
    private double price;
}
