package com.livia.projetos.gerenciador_estoque.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.livia.projetos.gerenciador_estoque.dtos.UserDTO;
import com.livia.projetos.gerenciador_estoque.models.UserEntity;
import com.livia.projetos.gerenciador_estoque.services.UserEntityService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {
    @Autowired
    UserEntityService userEntityService;

    @Operation(summary = "Cria um usuário (create user)")
    @PostMapping("users/register")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(userEntityService.save(userEntity), HttpStatus.CREATED);
    }

    @Operation(summary = "Faz login")
    @PostMapping("users/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userEntityService.login(userDTO), HttpStatus.OK);
    }

}
