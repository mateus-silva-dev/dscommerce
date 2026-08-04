package com.devsuperior.dscommerce.dto;

public class ProductDTO {

    public record Response(Long id, String name, String description, Double price, String imgUrl) { }
}

