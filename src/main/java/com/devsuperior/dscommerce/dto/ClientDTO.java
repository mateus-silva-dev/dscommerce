package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.User;

public record ClientDTO(
        Long id,
        String name
) {
    public ClientDTO(User client) {
        this(client.getId(), client.getName());
    }
}
