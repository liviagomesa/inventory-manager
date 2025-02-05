package com.livia.projetos.gerenciador_estoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livia.projetos.gerenciador_estoque.models.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

}
