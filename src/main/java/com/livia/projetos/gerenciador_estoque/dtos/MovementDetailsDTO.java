package com.livia.projetos.gerenciador_estoque.dtos;

import java.time.LocalDate;

import com.livia.projetos.gerenciador_estoque.models.MovementType;

public record MovementDetailsDTO(int productId, String productName, int movementId, MovementType movementType,
        LocalDate date, int quantity) {

}
