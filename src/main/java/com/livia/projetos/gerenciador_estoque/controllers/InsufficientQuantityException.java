package com.livia.projetos.gerenciador_estoque.controllers;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String message) {
        super(message);
    }
}
