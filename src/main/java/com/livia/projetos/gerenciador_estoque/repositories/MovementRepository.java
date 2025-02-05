package com.livia.projetos.gerenciador_estoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livia.projetos.gerenciador_estoque.models.Movement;
import com.livia.projetos.gerenciador_estoque.models.Product;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Integer> {
    public List<Movement> findByProduct(Product product);
}
