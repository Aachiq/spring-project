package com.example.restapi.controller;

import com.example.restapi.dto.ProductDetailsResponseDTO;
import com.example.restapi.dto.ProductsResponseDTO;
import com.example.restapi.exception.ForbiddenException;
import com.example.restapi.exception.UnauthorizedException;
import com.example.restapi.model.Product;
import com.example.restapi.model.Role;
import com.example.restapi.security.JwtUtils;
import com.example.restapi.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ProductsResponseDTO getProducts(HttpServletRequest request){
        Boolean logged = (Boolean) request.getAttribute("logged");

        System.out.println("logged " + logged);

        if (logged == null || !logged) {
            throw new UnauthorizedException("Login required");
        }

        // here give access
        List<Product> products = this.productService.findAllProducts();
        return new ProductsResponseDTO(products,"Products Fetched Successfully !");

    }

    @GetMapping("/{id}")
    public ProductDetailsResponseDTO getOneProduct(@PathVariable Long id, HttpServletRequest request){

        // 1. check login
        Boolean logged = (Boolean) request.getAttribute("logged");

        if (logged == null || !logged) {
            throw new UnauthorizedException("Login required");
        }

        // 2. check role
        String role = (String) request.getAttribute("userRole");

        if (role == null || !role.equals("ADMIN")) {
            throw new ForbiddenException("Admin only access");
        }

        // 3. busniss logic
        Optional<Product> foundProduct = productService.getProductById(id);
        if(foundProduct.isEmpty()){
            return new ProductDetailsResponseDTO(null,"Product By Id Not Found !");
        }
        return new ProductDetailsResponseDTO(foundProduct.get(),"Product By Id Fetched Successfully !");
    }
}
