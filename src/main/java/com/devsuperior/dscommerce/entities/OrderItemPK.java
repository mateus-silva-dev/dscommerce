package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
public class OrderItemPK {

    @ManyToOne
    @JoinColumn(name = "order_id")
    @Setter
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Setter
    private Product product;

    public OrderItemPK() { }

    public OrderItemPK(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof OrderItemPK that)) return false;

        return Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getOrder());
        result = 31 * result + Objects.hashCode(getProduct());
        return result;
    }
}
