package com.example.restapi.controller;

import com.example.restapi.dto.ProductDetailsResponseDTO;
import com.example.restapi.dto.ProductsResponseDTO;
import com.example.restapi.model.Product;
import com.example.restapi.model.Role;
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
    // here is public route
    @GetMapping
    public List<Product> getProducts(){
        return this.productService.findAllProducts();
    }
    */

    @GetMapping
    public ProductsResponseDTO getProducts(@RequestHeader(value = "Authorization") String authHeader){
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
    public ProductDetailsResponseDTO getOneProduct(@PathVariable Long id, @RequestHeader(value = "Authorization") String authHeader){
        // 1. Check header exists
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ProductDetailsResponseDTO(null,"Unauthorized: No token");
        }

        // 2. Extract token
        String token = authHeader.substring(7);

        // 3. Validate token (you must implement validation method)
        if (!jwtUtils.validateToken(token)) {
            return new ProductDetailsResponseDTO(null,"Unauthorized: Invalid token");
        }

        // 4. check role of user by extrating rol from token
        String userRole = jwtUtils.extractRole(token);

        System.out.println("extractRole : "+ userRole);

        if (!userRole.equals("ADMIN")) {
            return new ProductDetailsResponseDTO(null,"Unauthorized: Admin Resources");
        }

        Optional<Product> foundProduct = productService.getProductById(id);
        if(foundProduct.isEmpty()){
            return new ProductDetailsResponseDTO(null,"Product By Id Not Found !");
        }
        return new ProductDetailsResponseDTO(foundProduct.get(),"Product By Id Fetched Successfully !");
    }
}
