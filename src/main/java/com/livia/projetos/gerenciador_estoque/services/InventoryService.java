package com.livia.projetos.gerenciador_estoque.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livia.projetos.gerenciador_estoque.controllers.InsufficientQuantityException;
import com.livia.projetos.gerenciador_estoque.dtos.MovementCreateDTO;
import com.livia.projetos.gerenciador_estoque.dtos.MovementDetailsDTO;
import com.livia.projetos.gerenciador_estoque.dtos.ProductSpecificMovementDTO;
import com.livia.projetos.gerenciador_estoque.dtos.ProductSummary;
import com.livia.projetos.gerenciador_estoque.dtos.ProductDTO;
import com.livia.projetos.gerenciador_estoque.models.Movement;
import com.livia.projetos.gerenciador_estoque.models.MovementType;
import com.livia.projetos.gerenciador_estoque.models.Product;
import com.livia.projetos.gerenciador_estoque.repositories.MovementRepository;
import com.livia.projetos.gerenciador_estoque.repositories.ProductRepository;

@Service
public class InventoryService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MovementRepository movementRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public List<ProductSpecificMovementDTO> listProductMovements(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        List<Movement> movementsList = movementRepository.findByProduct(product);
        // Mapeando movements para DTO (uma forma otimizada de fazer isso seria com
        // stream().map() como demonstrado no método findMovementsByDate)
        List<ProductSpecificMovementDTO> movementsDTOList = new ArrayList<>();
        for (Movement m : movementsList) {
            movementsDTOList.add(
                    new ProductSpecificMovementDTO(m.getId(), m.getType(), m.getDate(), m.getQuantity()));
        }
        return movementsDTOList;
    }

    public List<ProductSummary> getMovementsCountPerProduct() {
        return productRepository.getMovementsCountPerProduct();
    }

    public List<Product> getProductsWithLowInventory(int maxQuantity) {
        List<Product> products = listProducts();
        Predicate<Product> predicate = product -> product.getQuantity() <= maxQuantity;
        // Outra forma de fazer isso seria com o método removeIf(predicate)
        return products.stream().filter(predicate).toList();
    }

    public List<MovementDetailsDTO> findMovementsByDate(LocalDate date) {
        Predicate<Movement> predicate = movement -> movement.getDate().equals(date);
        List<Movement> movements = movementRepository.findAll().stream().filter(predicate).toList();
        Function<Movement, MovementDetailsDTO> function = m -> new MovementDetailsDTO(m.getProduct().getId(),
                m.getProduct().getName(), m.getId(),
                m.getType(), m.getDate(), m.getQuantity());
        return movements.stream().map(function).toList();
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.name(), productDTO.quantity(), productDTO.price());
        productRepository.save(product);
        return product;
    }

    public Product editProduct(int id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        product.setName(productDTO.name());
        product.setQuantity(productDTO.quantity());
        product.setPrice(productDTO.price());
        productRepository.save(product);
        return product;
    }

    public Product addProductMovement(int id, MovementCreateDTO movementDTO) throws InsufficientQuantityException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        if (movementDTO.movementType().equals(MovementType.INPUT)) {
            product.setQuantity(product.getQuantity() + movementDTO.quantity());
        } else { // MovementType.OUTPUT
            if (product.getQuantity() - movementDTO.quantity() < 0)
                throw new InsufficientQuantityException(
                        "Output cancelled because there is insufficient product quantity in the inventory");
            product.setQuantity(product.getQuantity() - movementDTO.quantity());
        }
        productRepository.save(product);
        movementRepository.save(new Movement(product, movementDTO.movementType(), LocalDate.now(),
                movementDTO.quantity()));
        return product;
    }

    public void deleteProduct(int id) {
        productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
        productRepository.deleteById(id);
    }
}
