package com.livia.projetos.gerenciador_estoque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.livia.projetos.gerenciador_estoque.models.UserEntity;
import com.livia.projetos.gerenciador_estoque.repositories.UserEntityRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Encontrando o usuário no banco de dados com a entidade personalizada
        UserEntity userEntity = userEntityRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Converte os valores do enum Role para Strings
        String[] rolesArray = userEntity.getRoles().stream()
                .map(Enum::name) // Converte cada Role para seu nome como String
                .toArray(String[]::new);

        // Mas precisamos retornar um UserDetails (que é apenas um objeto usado em
        // memória para autenticação e autorização)
        return User.withUsername(username).password(userEntity.getPassword())
                .roles(rolesArray).build(); // Passa as roles como String[]
    }

}
