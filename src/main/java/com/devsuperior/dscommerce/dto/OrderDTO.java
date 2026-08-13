package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderStatus;

import java.time.Instant;
import java.util.List;

public record OrderDTO(
        Long id,
        Instant moment,
        OrderStatus status,

        ClientDTO client,

        PaymentDTO payment,

        List<OrderItemDTO> items
) {
    public OrderDTO(Order entity) {
        this(
                entity.getId(),
                entity.getMoment(),
                entity.getStatus(),
                new ClientDTO(entity.getClient()),
                (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment()),
                entity.getItems().stream().map(OrderItemDTO::new).toList()
        );
    }

    public Double getTotal() {
        return items.stream().mapToDouble(OrderItemDTO::getSubTotal).sum();
    }
}
