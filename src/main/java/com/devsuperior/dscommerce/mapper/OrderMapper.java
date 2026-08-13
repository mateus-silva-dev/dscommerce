package com.devsuperior.dscommerce.mapper;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, UserMapper.class})
public interface OrderMapper {

    OrderDTO toDTO(Order entity);
    Order toEntity(OrderDTO dto);
}
