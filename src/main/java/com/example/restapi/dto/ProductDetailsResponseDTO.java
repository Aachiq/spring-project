package com.example.restapi.dto;

import com.example.restapi.model.Product;

public class ProductDetailsResponseDTO {
    private Product product;
    private String message;

    public ProductDetailsResponseDTO(Product product, String message){
        this.product = product;
        this.message = message;
    }

    public Product getProduct(){
        return this.product;
    }

    public String getMessage(){
        return this.message;
    }
}
