package com.example.restapi.dto;

import com.example.restapi.model.Product;

import java.util.List;

public class ProductsResponseDTO {

    private List<Product> products;
    private String message;

    public ProductsResponseDTO(List<Product> products, String message){
        this.products = products;
        this.message = message;
    }

    public List<Product> getProducts(){
        return this.products;
    }

    public String getMessage(){
        return this.message;
    }
}
