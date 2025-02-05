package com.livia.projetos.gerenciador_estoque.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    // Quando uma exceção ocorre dentro de um filtro, ela não passa pelo
    // GlobalExceptionHandler (ele age somente durante a execução dos controllers).
    // Assim, vamos capturar todas as exceções lançadas aqui e usar o
    // HandlerExceptionResolver para delegar o tratamento da exceção para o handler
    // centralizado (o GlobalExceptionHandler)
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Obtendo o cabeçalho Authorization
            String token = request.getHeader("Authorization");

            // Caso não haja token, prosseguir para o próximo filtro (cliente pode estar em
            // rota pública)
            if (token == null || token.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            // Retirando o prefiro Bearer adicionado pelos navegadores
            if (token.startsWith("Bearer")) {
                token = token.substring(7);
            }

            // Salvando as informações do usuário no contexto de segurança
            Authentication userInfo = jwtTokenProvider.getUserInfo(token);
            SecurityContextHolder.getContext().setAuthentication(userInfo);

            // Prosseguindo para os demais filtros do Filter Chain
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // Delegando o tratamento para o HandlerExceptionResolver, que invocará seu
            // GlobalExceptionHandler
            resolver.resolveException(request, response, null, e);
        }
    }

}
