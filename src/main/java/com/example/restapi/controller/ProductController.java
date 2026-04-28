package com.example.restapi.controller;

import com.example.restapi.dto.ProductsResponseDTO;
import com.example.restapi.model.Product;
import com.example.restapi.security.JwtUtils;
import com.example.restapi.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final JwtUtils jwtUtils;

    public ProductController(ProductService productService, JwtUtils jwtUtils){
        this.productService = productService;
        this.jwtUtils = jwtUtils;
    }

    /*
    @GetMapping
    public List<Product> getProducts(){
        return this.productService.findAllProducts();
    }
    */

    @GetMapping
    public ProductsResponseDTO getProducts(@RequestHeader(value = "Authorization", required = false) String authHeader){
        // 1. Check header exists
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ProductsResponseDTO(null,"Unauthorized: No token");
        }

        // 2. Extract token
        String token = authHeader.substring(7);

        // 3. Validate token (you must implement validation method)
        if (!jwtUtils.validateToken(token)) {
            return new ProductsResponseDTO(null,"Unauthorized: Invalid token");
        }

        // here give access
        List<Product> products = this.productService.findAllProducts();
        return new ProductsResponseDTO(products,"Unauthorized: No token");

    }

    @GetMapping("/{id}")
    public Optional<Product> getOneProduct(@PathVariable Long id){
        return productService.getProductById(id);
    }
}
