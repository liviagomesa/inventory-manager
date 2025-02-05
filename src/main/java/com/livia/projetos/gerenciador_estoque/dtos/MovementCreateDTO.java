package com.livia.projetos.gerenciador_estoque.dtos;

import com.livia.projetos.gerenciador_estoque.models.MovementType;

import jakarta.validation.constraints.Positive;

public record MovementCreateDTO(
        @Positive(message = "Quantity must be a positive value") int quantity,
        // Desnecessário usar @NotNull, pois quando o Jackson tenta desserializar um
        // valor vazio ("") para o enum, resulta em uma HttpMessageNotReadableException,
        // que é lançada antes mesmo da validação @NotNull ser executada. Vamos capturar
        // a exceção gerada automaticamente no GlobalExceptionHandler
        MovementType movementType) {

}
