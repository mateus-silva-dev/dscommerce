package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_order")
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Setter
    private Instant moment;

    @Setter
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Setter
    private User client;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @Setter
    private Payment payment;

    @OneToMany(mappedBy = "id.order")
    @Setter(AccessLevel.NONE)
    private Set<OrderItem> items = new HashSet<>();

    public Order() { }

    public Order(Long id, Instant moment, OrderStatus status, User client, Payment payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public List<Product> getProducts() {
        return items.stream().map(OrderItem::getProduct).toList();
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;

        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
