package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "tb_product")
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    private String name;

    @Column(columnDefinition = "TEXT")
    @Setter
    private String description;

    @Setter
    private Double price;

    @Setter
    private String imgUrl;

    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Setter(AccessLevel.NONE)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "id.product")
    @Setter(AccessLevel.NONE)
    private Set<OrderItem> items = new HashSet<>();

    public Product() { }

    public Product(String name, String description, Double price, String imgUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public void updateData(String name, String description, Double price, String imgUrl, Set<Category> category) {
        if (name != null && !name.isBlank()) this.name = name;
        if (description != null && !description.isBlank()) this.description = description;
        if (price != null && price > 0) this.price = price;
        if (imgUrl != null && !imgUrl.isBlank()) this.imgUrl = imgUrl;
        categories.clear();
        if (category != null) {
            categories.addAll(category);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;

        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}