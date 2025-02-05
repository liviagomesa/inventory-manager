package com.livia.projetos.gerenciador_estoque.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "username must not be blank") String username,
        @NotBlank(message = "password must not be blank") String password) {

}
