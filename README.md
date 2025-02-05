# Gerenciador de Estoque

Este é um sistema de gerenciamento de estoque desenvolvido em Java com Spring. O sistema é acessado via endpoints REST e permite o gerenciamento de produtos e suas movimentações de entrada (input) e saída (output), além de fornecer informações úteis sobre o estoque.

## Funcionalidades

- **Cadastro de produtos:** Permite criar novos produtos no sistema.
- **Edição de produtos:** Atualização dos detalhes de um produto.
- **Exclusão de produtos:** Remoção de um produto do estoque.
- **Movimentações de produtos:** Adição de movimentações (entrada/saída) no estoque.
- **Listagem de produtos:** Exibe todos os produtos cadastrados.
- **Listagem de movimentações:** Exibe as movimentações de um produto específico.
- **Resumo de movimentações:** Exibe um resumo da quantidade de movimentações por produto.
- **Produtos de baixo estoque:** Lista produtos com quantidades abaixo de um limite.
- **Movimentações por data:** Lista movimentações realizadas em uma data específica.
- **Autenticação e autorização:** Controle de acesso com base em roles de usuários. Apenas usuários com permissões adequadas podem executar determinadas operações (ex.: usuários ADMIN têm acesso total).

## Tecnologias Utilizadas

- **Linguagem:** Java  
- **Frameworks e bibliotecas:** Spring Boot, Spring Data JPA, Spring Security, Spring Web, Lombok, Swagger, JWT (Auth0)  
- **Banco de dados:** PostgreSQL

## Como Executar a Aplicação

A aplicação está hospedada no Railway, e você pode consumir os endpoints via Postman, utilizando um token JWT no cabeçalho da requisição.

### Variáveis de Ambiente

No ambiente de produção (Railway), foi necessário configurar uma variável de ambiente para o segredo do algoritmo JWT.

### Exemplo de Uso

1. **Criar um Produto:**  
   - Método: `POST`
   - URL: `/products`
   - Cabeçalho: `Authorization: Bearer <seu-token-jwt>`
   - Corpo da requisição:  
     ```json
     {
       "name": "Exemplo de Produto",
       "quantity": 10,
       "price": 29.99
     }
     ```

2. **Adicionar uma Movimentação:**  
   - Método: `PUT`
   - URL: `/products/{id}/movements`
   - Cabeçalho: `Authorization: Bearer <seu-token-jwt>`
   - Corpo da requisição:  
     ```json
     {
       "quantity": 5,
       "movementType": "INPUT"
     }
     ```

## Estrutura de Pastas

- **`controllers`:** Contém os endpoints da aplicação.
- **`dtos`:** Objetos para transferência de dados (Data Transfer Objects).
- **`models`:** Entidades do banco de dados, como `Product`, `Movement` e `UserEntity`, e enums.
- **`repositories`:** Interfaces para acesso e manipulação dos dados.
- **`security`:** Configuração de autenticação, autorização, codificação de senhas e JWT.
- **`services`:** Regras de negócio e manipulação dos dados enviados ao controller.

## Testes

Os testes foram realizados exclusivamente via Postman. Você pode acessar os testes utilizando o [link compartilhado do Postman](https://www.postman.com/material-meteorologist-80863154/gerenciador-de-estoque-com-jwt/overview).

## Autor e Agradecimentos

Desenvolvido por Livia como parte de seu portfólio.  
Agradecimentos ao curso **"Back-End com Java"** da DIO, que forneceu uma base sólida para o aprendizado das tecnologias utilizadas.

## Limitações Conhecidas

- A aplicação não possui testes automatizados; todos os testes foram realizados via Postman.
- Por enquanto, não há uma interface frontend. A interação com a aplicação se dá exclusivamente via ferramentas como Postman.