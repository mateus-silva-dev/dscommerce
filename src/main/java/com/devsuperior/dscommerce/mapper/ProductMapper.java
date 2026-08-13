package com.devsuperior.dscommerce.mapper;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    ProductMinDTO toMinDTO(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDTO dto);
}
