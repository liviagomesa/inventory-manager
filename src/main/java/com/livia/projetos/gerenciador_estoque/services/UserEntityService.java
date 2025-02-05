package com.livia.projetos.gerenciador_estoque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.livia.projetos.gerenciador_estoque.dtos.UserDTO;
import com.livia.projetos.gerenciador_estoque.models.UserEntity;
import com.livia.projetos.gerenciador_estoque.repositories.UserEntityRepository;
import com.livia.projetos.gerenciador_estoque.security.JwtTokenProvider;

@Service
public class UserEntityService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public UserEntity save(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userEntityRepository.save(user);
    }

    public String login(UserDTO userDTO) {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password(), null));
        return jwtTokenProvider.generateToken(userDTO.username());
    }
}
