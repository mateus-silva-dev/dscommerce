package com.devsuperior.dscommerce.mapper;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = CategoryMapper.class,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductDTO toDTO(Product product);
    ProductMinDTO toMinDTO(Product product);
    Product toEntity(ProductDTO dto);

}
