package com.livia.projetos.gerenciador_estoque.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductDTO(
                @NotBlank(message = "Product name must not be empty") String name,
                @Positive(message = "Quantity must not be negative") int quantity,
                @Positive(message = "Price must not be negative") double price) {

}
