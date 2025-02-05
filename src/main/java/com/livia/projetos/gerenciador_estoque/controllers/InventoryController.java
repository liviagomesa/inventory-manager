package com.livia.projetos.gerenciador_estoque.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.livia.projetos.gerenciador_estoque.dtos.MovementCreateDTO;
import com.livia.projetos.gerenciador_estoque.dtos.MovementDetailsDTO;
import com.livia.projetos.gerenciador_estoque.dtos.ProductSpecificMovementDTO;
import com.livia.projetos.gerenciador_estoque.dtos.ProductSummary;
import com.livia.projetos.gerenciador_estoque.dtos.ProductDTO;
import com.livia.projetos.gerenciador_estoque.models.Product;
import com.livia.projetos.gerenciador_estoque.services.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @Operation(summary = "Lista todos os produtos (list all products) - EMPLOYEE, MANAGER, ADMIN")
    @GetMapping("products")
    public ResponseEntity<List<Product>> listProducts() {
        return new ResponseEntity<>(inventoryService.listProducts(), HttpStatus.OK);
    }

    @Operation(summary = "Lista todas as movimentações de um produto escolhido (list all movements of a chosen product) - EMPLOYEE, MANAGER, ADMIN")
    @GetMapping("products/{id}/movements")
    public ResponseEntity<List<ProductSpecificMovementDTO>> listProductMovements(@PathVariable int id) {
        return new ResponseEntity<>(inventoryService.listProductMovements(id), HttpStatus.OK);
    }

    @Operation(summary = "Lista quantas movimentações foram feitas para cada produto (list movements count for each product) - AUDITOR, MANAGER, ADMIN")
    @GetMapping("movements/summary")
    public ResponseEntity<List<ProductSummary>> getMovementsCountPerProduct() {
        return new ResponseEntity<>(inventoryService.getMovementsCountPerProduct(), HttpStatus.OK);
    }

    @Operation(summary = "Lista produtos com estoque abaixo de uma quantidade fornecida (list products with inventory below a given quantity) - MANAGER, ADMIN")
    @GetMapping("products/low-inventory")
    // A quantidade máxima será passada no query parameter, ex.:
    // "products/low-inventory?maxQuantity=10"
    public ResponseEntity<List<Product>> getProductsWithLowInventory(@RequestParam int maxQuantity) {
        return new ResponseEntity<>(inventoryService.getProductsWithLowInventory(maxQuantity), HttpStatus.OK);
    }

    @Operation(summary = "Lista todas as movimentações registradas em uma data fornecida (list all movements registered on a given date) - AUDITOR, MANAGER, ADMIN")
    @GetMapping("movements/by-date")
    public ResponseEntity<List<MovementDetailsDTO>> findMovementsByDate(@RequestParam LocalDate date) {
        return new ResponseEntity<>(inventoryService.findMovementsByDate(date), HttpStatus.OK);
    }

    @Operation(summary = "Cria um produto (create a product) - ADMIN")
    @PostMapping("products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(inventoryService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Edita um produto (edit product) - ADMIN")
    @PutMapping("products/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable int id, @Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(inventoryService.editProduct(id, productDTO), HttpStatus.OK);
    }

    @Operation(summary = "Adiciona movimentação de um produto (add product movement) - MANAGER, ADMIN")
    @PutMapping("products/{id}/movements")
    public ResponseEntity<Product> addProductMovement(@PathVariable int id,
            @Valid @RequestBody MovementCreateDTO movementDTO) {
        return new ResponseEntity<>(inventoryService.addProductMovement(id, movementDTO), HttpStatus.OK);
    }

    @Operation(summary = "Exclui um produto (delete product) - ADMIN")
    @DeleteMapping("products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        inventoryService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
