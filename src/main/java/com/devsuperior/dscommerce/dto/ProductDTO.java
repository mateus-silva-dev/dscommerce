package com.devsuperior.dscommerce.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record ProductDTO(
            Long id,

            @NotBlank(message = "Campo requerido")
            @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
            String name,

            @NotBlank(message = "Campo requerido")
            @Size(min = 10, message = "A descrição precisa ter no minimo 10 caracteres")
            String description,

            @NotNull(message = "Campo requerido")
            @Positive(message = "O preço deve ser positivo")
            Double price,

            @NotBlank(message = "Campo requerido")
            String imgUrl,

            @NotNull(message = "Campo requerido")
            @NotEmpty(message = "Informe ao menos uma categoria")
            List<CategoryDTO> categories
) {
    public ProductDTO {
        categories = (categories != null) ? List.copyOf(categories) : List.of();
    }
}