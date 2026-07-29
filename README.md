# DSCommerce - Backend API

Esta é a API rest do **DSCommerce**, um sistema de e-commerce desenvolvido com base nos requisitos fornecidos pela **DevSuperior**. O projeto consiste em um sistema com um modelo de domínio abrangente, que explora diversos tipos de relacionamentos entre entidades de negócio e implementa regras de negócio, validações e controle de acesso.

---

## 💻 Visão Geral do Sistema

O backend gerencia o cadastro de usuários, produtos e categorias, além de processar o fluxo completo de pedidos e carrinho de compras.

* **Usuários e Autenticação:** Controla usuários com perfis de acesso **Cliente** (padrão) e **Administrador**. Usuários anônimos podem se autenticar para obter um token válido.
* **Catálogo de Produtos:** Disponibiliza listagem de produtos com paginação e busca filtrada por nome.
* **Pedidos:** Registra as compras efetuadas, mantendo o histórico de preços dos itens vendidos e gerenciando o ciclo de vida dos status do pedido (*Aguardando Pagamento, Pago, Enviado, Entregue e Cancelado*).

---

## 📐 Modelo de Domínio (Conceitual)

A estrutura de dados e os relacionamentos mapeados no banco de dados baseiam-se nas seguintes entidades:

* **Product**: `id`, `name`, `description`, `price`, `imgUrl`.
* **Category**: `id`, `name`.
* **User**: `id`, `name`, `email`, `phone`, `birthDate`, `password`, `roles`.
* **Order**: `id`, `moment`, `status` (`OrderStatus`).
* **OrderItem**: `quantity`, `price` (armazena o preço histórico do momento da venda).
* **Payment**: `id`, `moment`.

---

## 🔒 Regras de Acesso por Endpoint (Casos de Uso)

| Funcionalidade / Endpoint | Descrição Backend | Nível de Acesso |
| :--- | :--- | :--- |
| **Login** | Valida credenciais e retorna um token de acesso válido. | Público |
| **Sign up** | Permite o autocadastro de novos usuários no sistema. | Público |
| **Consultar catálogo** | Retorna uma lista paginada de produtos (padrão de 12 itens por página), ordenados por nome e filtráveis por parte do termo informado. | Público |
| **Gerenciar carrinho** | Processa a adição, alteração de quantidade ou remoção de itens, calculando subtotais e valor total. | Público |
| **Registrar pedido** | Recebe os dados do carrinho de compras, gera um novo pedido com status `WAITING_PAYMENT` e retorna o ID gerado (limpa o carrinho após o sucesso). | Usuário Logado |
| **Atualizar perfil** | Permite que o usuário logado modifique suas próprias informações cadastrais. | Usuário Logado |
| **Visualizar pedidos** | Lista o histórico de pedidos associados exclusivamente ao usuário logado. | Usuário Logado |
| **Manter produtos** | Operações de CRUD completas para gerenciamento de produtos. | Somente Admin |
| **Manter categorias** | Operações de CRUD completas para gerenciamento de categorias. | Somente Admin |
| **Manter usuários** | Operações de CRUD completas para gerenciamento de usuários. | Somente Admin |
| **Registrar pagamento** | Salva as informações e o instante em que o pagamento de um pedido foi efetuado. | Somente Admin |
| **Reportar pedidos** | Gera relatório e listagem de vendas/pedidos com filtros aplicados por data. | Somente Admin |

---

## ⚠️ Validações e Regras de Negócio

O backend aplica regras rigorosas para a persistência de dados, especialmente nas operações de escrita do catálogo de produtos:

* **Validação de Campos (Inserir/Atualizar Produtos):**
  * **Nome:** Obrigatório, deve conter entre 3 e 80 caracteres.
  * **Preço:** Deve ser um valor estritamente positivo.
  * **Descrição:** Obrigatória, com tamanho mínimo de 10 caracteres.
  * **Categorias:** O produto deve obrigatoriamente possuir pelo menos 1 categoria vinculada.
* **Exceções de Negócio Tratadas:**
  * **Id não encontrado:** Retorna erro customizado caso a entidade buscada para atualização ou deleção não exista.
  * **Integridade referencial:** Impede a deleção de produtos que possuam vínculos com outras entidades do sistema, lançando uma exceção de integridade dos dados.
  * **Credenciais inválidas:** Retorna erro apropriado ao falhar no processo de autenticação.

---
O projeto foi desenvolvido durante o curso da DevSuperior.