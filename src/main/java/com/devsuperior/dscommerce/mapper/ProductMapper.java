package com.devsuperior.dscommerce.mapper;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import org.modelmapper.ModelMapper;

public class ProductMapper {

    private static final ModelMapper MAPPER = new ModelMapper();
    static {
        MAPPER.createTypeMap(Product.class, ProductDTO.Response.class)
                .setConverter(context -> {
                    Product source = context.getSource();
                    return new ProductDTO.Response(
                            source.getId(),
                            source.getName(),
                            source.getDescription(),
                            source.getPrice(),
                            source.getImgUrl()
                    );
                });
    }

    private ProductMapper() { }

    public static ProductDTO.Response toDTO(Product entity) {
        return MAPPER.map(entity, ProductDTO.Response.class);
    }

    public static Product toEntity(ProductDTO.Response dto) {
        return Product.of(dto.name(), dto.description(), dto.price(), dto.imgUrl());
    }
}
