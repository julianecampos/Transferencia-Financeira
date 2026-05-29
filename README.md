# Transferência Financeira

Sistema de agendamento de transferências financeiras desenvolvido como avaliação prática.

## Sobre o Projeto:

O sistema permite que o usuário agende transferências financeiras entre contas, calculando automaticamente a taxa com base na data de transferência. Também é possível visualizar o extrato de todos os agendamentos realizados.

## Tecnologias Utilizadas:

### Back-end

- **Java 11**
- **Spring Boot 2.7.18**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **H2 Database** (banco de dados em memória)
- **Lombok**
- **Maven**

### Front-end

- **Vue.js 3**
- **Axios**

### Ferramentas

- **VS Code**
- **Git / GitHub**
- **Thunder Client**

## Decisões Arquiteturais:

### Back-end

A aplicação segue o padrão em camadas:

- **Model** — entidade `Agendamento` mapeada com JPA
- **Repository** — interface que estende `JpaRepository` para acesso ao banco
- **Service** — regras de negócio e cálculo de taxa
- **Controller** — endpoints REST da API

O banco de dados utilizado foi o **H2 em memória**, conforme solicitado. Ele sobe automaticamente junto com a aplicação sem necessidade de configuração externa.

O cálculo da taxa é feito no `AgendamentoService` com base na diferença em dias entre a data atual e a data de transferência:

| Dias    | Taxa Fixa | Taxa % |
| ------- | --------- | ------ |
| 0       | R$ 3,00   | 2,5%   |
| 1 a 10  | R$ 12,00  | 0%     |
| 11 a 20 | R$ 0,00   | 8,2%   |
| 21 a 30 | R$ 0,00   | 6,9%   |
| 31 a 40 | R$ 0,00   | 4,7%   |
| 41 a 50 | R$ 0,00   | 1,7%   |

Caso a data não se encaixe em nenhuma faixa, uma exceção é lançada e o agendamento não é realizado.

### Front-end

O front-end foi desenvolvido em **Vue.js 3**. A comunicação com a API é feita via **Axios**. A aplicação possui dois fluxos principais:

- Formulário para agendar uma nova transferência
- Tabela de extrato listando todos os agendamentos realizados

## Estrutura do Projeto:

Transferencia-Financeira/
Transferencia-Financeira/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/transferencias/transferencias_api/
│   │   │   │   ├── controller/AgendamentoController.java
│   │   │   │   ├── model/Agendamento.java
│   │   │   │   ├── repository/AgendamentoRepository.java
│   │   │   │   └── service/AgendamentoService.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
└── frontend/
├── src/
│   ├── App.vue
│   └── main.js
└── package.json

## Como Rodar o Projeto:

### Pré-requisitos

- Java 11
- Maven
- Node.js
- Vue CLI (`npm install -g @vue/cli`)

### Back-end

```bash
cd backend
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`

### Front-end

```bash
cd frontend
npm install
npm run serve
```

O front-end ficará disponível em `http://localhost:8081`

### Banco de Dados H2

Com o back-end rodando, acesse o console do H2 em:
`http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:transferencias`
- **User Name:** `sa`
- **Password:** _(deixar em branco)_

## Endpoints da API:

| Método | Endpoint        | Descrição                   |
| ------ | --------------- | --------------------------- |
| POST   | `/agendamentos` | Cria um novo agendamento    |
| GET    | `/agendamentos` | Lista todos os agendamentos |

### Exemplo de requisição POST

```json
{
  "contaOrigem": "1234567890",
  "contaDestino": "0987654321",
  "valor": 1000.0,
  "dataTransferencia": "2026-06-10"
}
```

### Exemplo de resposta

```json
{
  "id": 1,
  "contaOrigem": "1234567890",
  "contaDestino": "0987654321",
  "valor": 1000.0,
  "taxa": 82.0,
  "dataTransferencia": "2026-06-10",
  "dataAgendamento": "2026-05-28"
}
```

## Testes:

Os endpoints foram testados via **Thunder Client** diretamente no VS Code:

- **POST /agendamentos** — criação de agendamento com cálculo de taxa validado para todas as faixas de dias
- **GET /agendamentos** — listagem de todos os agendamentos retornando corretamente
- Tentativa de agendamento com data fora das faixas retornou erro com mensagem correta
- Integração entre front-end e back-end testada e funcionando corretamente
