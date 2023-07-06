package com.example.springsecuritytester.controller;

import com.example.springsecuritytester.domain.Product;
import com.example.springsecuritytester.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Product API")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Constructor to inject the ProductService
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }

    @GetMapping("/welcome") // open to all // unrestricted api endpoint
    @Operation(summary = "Welcome to the Product API || Unrestricted endpoint")
    public String welcome() {
        return "Welcome to the Product API - NonSecure Endpoint";
    }

    // GET method to get all products
    // Only Admin can see all the products, not any user
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Authorizing the endpoint access
    @GetMapping("/all") // restricted only to admin user
    @Operation(summary = "Get all products || Restricted to ROLE_ADMIN")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET method to get product by id
    // Both the admin and user can see the product by id
    // Authorizing the endpoint access
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")  // both admin and user can see the product by id
    @GetMapping("/{id}")    // both admin and user can see the product by id
    @Operation(summary = "Get product by id || Restricted to ROLE_USER or ROLE_ADMIN")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }


}
