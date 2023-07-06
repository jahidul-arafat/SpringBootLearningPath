package com.example.springsecuritytester.service;

import com.example.springsecuritytester.domain.Product;
import com.example.springsecuritytester.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    // Use constructor injection, instead of @Autowired.
//    public ProductService(List<Product> productList) {
//        this.productList = productList;
//    }

    // method to create a List of 100 products using @PostConstruct annotation and I
    // Maven clean and install will rebuild the application and will load all the products into the Product Database
    // Once the Rebuild completed, make sure to comment this init() method
    // Else duplication of products will occur.
//    @PostConstruct
//    public void init() {
//        /* to be deleted
//        productList = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
//            productList.add(Product.builder()
//                    .productId(i)
//                    .name("product " + i)
//                    .qty(new Random().nextInt(10))
//                    .price(new Random().nextInt(5000))
//                    .build());
//    }
//         */
//        // IntStream.range(0, 10).forEach(System.out::println);
//        // cant be final as we are assigning values to it at init() stage
//        List<Product> productList = IntStream.rangeClosed(1, 100)
//                .mapToObj(i -> Product.builder()
//                        .id(i)
//                        .name("Product " + i)
//                        .qty(new Random().nextInt(100))
//                        .price(new Random().nextInt(10000))
//                        .build())
//                .collect(Collectors.toList());
//        productRepository.saveAll(productList);
//    }

    // method to get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // method to get product by id
    public Product getProductById(int id) {
//        return productList.stream()
//              .filter(p -> p.getId() == id)
//              .findAny()
//                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
