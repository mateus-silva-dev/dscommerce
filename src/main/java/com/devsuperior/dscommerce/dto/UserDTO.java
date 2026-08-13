package com.devsuperior.dscommerce.dto;

import java.time.LocalDate;
import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        String phone,
        LocalDate birthDate,

        List<String> roles
) {
    public UserDTO {
        roles = (roles != null) ? List.copyOf(roles) : List.of();
    }
}
