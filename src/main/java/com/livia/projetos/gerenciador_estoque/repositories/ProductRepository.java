package com.livia.projetos.gerenciador_estoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.livia.projetos.gerenciador_estoque.dtos.ProductSummary;
import com.livia.projetos.gerenciador_estoque.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT new com.livia.projetos.gerenciador_estoque.dtos.ProductSummary(product.id, product.name, COUNT(movement)) "
            +
            "FROM Product product LEFT JOIN Movement movement ON movement.product = product " +
            "GROUP BY product.id, product.name " +
            "ORDER BY COUNT(movement) DESC")
    List<ProductSummary> getMovementsCountPerProduct();
}
