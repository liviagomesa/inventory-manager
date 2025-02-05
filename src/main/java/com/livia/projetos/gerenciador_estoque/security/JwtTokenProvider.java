package com.livia.projetos.gerenciador_estoque.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.livia.projetos.gerenciador_estoque.services.UserDetailsServiceImpl;

import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secretkey}")
    private String secretKey;

    // Necessário iniciar via @PostConstruct para dar tempo da secretKey ser
    // injetada
    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    public String generateToken(String username) {
        return JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(3, ChronoUnit.HOURS))
                .withSubject(username)
                .sign(algorithm);
    }

    public Authentication getUserInfo(String token) {
        String username = JWT.require(algorithm).build().verify(token).getSubject();
        UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
    }

}
