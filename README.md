# DSCommerce — Backend API

API REST de um sistema de comércio eletrônico desenvolvida durante o curso **Java Spring Professional**, da DevSuperior. O projeto oferece catálogo de produtos, categorias, pedidos e identificação do usuário autenticado, com controle de acesso baseado em perfis.

## Tecnologias

- Java 21
- Spring Boot 3.5.8
- Spring Web e Bean Validation
- Spring Data JPA
- Spring Security
- Spring Authorization Server e OAuth2 Resource Server
- JWT com assinatura RSA
- H2 Database
- MapStruct e Lombok
- Maven

## Funcionalidades atuais

- Consulta pública de produtos, com paginação e filtro por nome
- Consulta pública de categorias
- Cadastro, atualização e exclusão de produtos por administradores
- Criação e consulta de pedidos por usuários autenticados
- Consulta dos dados do usuário autenticado
- Login OAuth2 com emissão de access token JWT
- Autorização por perfis `ROLE_CLIENT` e `ROLE_ADMIN`
- Validação de dados e tratamento centralizado de exceções

## Autenticação com OAuth2

A aplicação atua ao mesmo tempo como **Authorization Server** e **Resource Server**. O endpoint de login utiliza um grant customizado do tipo `password` e retorna um access token JWT no formato Bearer.

### Obter um token

Faça uma requisição `POST` para `http://localhost:8080/oauth2/token`. As credenciais do cliente são enviadas por HTTP Basic, enquanto as credenciais do usuário são enviadas como formulário (`application/x-www-form-urlencoded`).

```bash
curl -X POST http://localhost:8080/oauth2/token \
  -u dscommerceid:dscommercesecret \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password&username=maria@gmail.com&password=123456"
```

Exemplo de resposta:

```json
{
  "access_token": "eyJ...",
  "token_type": "Bearer",
  "expires_in": 86400
}
```

O JWT inclui as claims customizadas `username` e `authorities`. Para acessar um recurso protegido, envie o token no cabeçalho:

```http
Authorization: Bearer SEU_ACCESS_TOKEN
```

Exemplo:

```bash
curl http://localhost:8080/users/me \
  -H "Authorization: Bearer SEU_ACCESS_TOKEN"
```

> O grant `password` foi implementado de forma customizada para fins didáticos. Para aplicações públicas em produção, prefira fluxos atuais como Authorization Code com PKCE.

## Perfis e usuários de teste

Ao executar a aplicação com o perfil `test`, os dados iniciais são carregados automaticamente:

| Usuário | Senha | Perfis |
| --- | --- | --- |
| `maria@gmail.com` | `123456` | `ROLE_CLIENT` |
| `alex@gmail.com` | `123456` | `ROLE_CLIENT`, `ROLE_ADMIN` |

## Endpoints

| Método | Endpoint | Acesso | Descrição |
| --- | --- | --- | --- |
| `POST` | `/oauth2/token` | Público | Autentica o usuário e emite o JWT |
| `GET` | `/products` | Público | Lista produtos com paginação e filtro por nome |
| `GET` | `/products/{id}` | Público | Busca um produto por ID |
| `POST` | `/products` | Admin | Cadastra um produto |
| `PUT` | `/products/{id}` | Admin | Atualiza um produto |
| `DELETE` | `/products/{id}` | Admin | Exclui um produto |
| `GET` | `/categories` | Público | Lista as categorias |
| `GET` | `/orders/{id}` | Cliente ou Admin | Consulta um pedido permitido ao usuário |
| `POST` | `/orders` | Cliente | Registra um novo pedido para o usuário autenticado |
| `GET` | `/users/me` | Cliente ou Admin | Retorna os dados do usuário autenticado |

Exemplo de busca paginada:

```http
GET /products?name=pc&page=0&size=12&sort=name,asc
```

## Configuração

As principais configurações podem ser sobrescritas por variáveis de ambiente:

| Variável | Valor padrão | Descrição |
| --- | --- | --- |
| `APP_PROFILE` | `test` | Perfil ativo do Spring |
| `CLIENT_ID` | `dscommerceid` | ID do cliente OAuth2 |
| `CLIENT_SECRET` | `dscommercesecret` | Segredo do cliente OAuth2 |
| `JWT_DURATION` | `86400` | Duração do access token, em segundos |
| `CORS_ORIGINS` | `http://localhost:3000,http://localhost:5173` | Origens permitidas pelo CORS |

Não utilize os valores padrão de cliente e segredo em produção.

## Executando o projeto

Pré-requisitos: Java 21 instalado. O Maven Wrapper já está incluído no repositório.

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`. Com o perfil `test`, o console do H2 pode ser acessado em `http://localhost:8080/h2-console`, usando:

- JDBC URL: `jdbc:h2:mem:testdb`
- User Name: `sa`
- Password: vazio

## Modelo de domínio

- **Product:** produto do catálogo, associado a uma ou mais categorias
- **Category:** classificação dos produtos
- **User:** cliente ou administrador, identificado pelo e-mail
- **Role:** perfil de autorização do usuário
- **Order:** pedido realizado por um cliente
- **OrderItem:** item do pedido, com quantidade e preço registrado no momento da compra
- **Payment:** pagamento associado a um pedido

## Validações e erros

Produtos exigem nome entre 3 e 80 caracteres, preço positivo, descrição com ao menos 10 caracteres e pelo menos uma categoria. A API também trata recursos inexistentes, violações de integridade, acesso proibido e erros de validação, retornando respostas HTTP apropriadas.

---

Projeto desenvolvido durante o curso da [DevSuperior](https://devsuperior.com.br/).
