# RafaCar - Backend

API em Java Spring Boot para gestão de veículos e vendas do sistema RafaCar.

## Tecnologias
- Java 17 + Spring Boot 3.3
- PostgreSQL
- JPA/Hibernate
- Docker (opcional para banco)
- Swagger/OpenAPI

## Como rodar

### 1) Subir o banco com Docker
```bash
docker compose up -d
```

### 2) Rodar a API
```bash
./mvnw spring-boot:run
# ou:
mvn spring-boot:run
```

Acesse o Swagger em: `http://localhost:8080/swagger`

## Endpoints principais

- `POST /veiculos` — cria veículo
- `GET /veiculos` — lista veículos
- `GET /veiculos/{id}` — busca por id
- `PUT /veiculos/{id}` — atualiza veículo
- `DELETE /veiculos/{id}` — remove veículo

- `POST /vendas` — cria venda (com campo `outros` por venda)
- `GET /vendas` — lista vendas em formato calculado (DTO)


# Patches de Despesas / Financeiro — RafaCar

## Novos arquivos
- `model/Despesa.java`
- `repository/DespesaRepository.java`
- `service/DespesaService.java`
- `controller/DespesaController.java`
- `controller/FinanceiroController.java`


## Endpoints adicionados
- `POST /despesas` — body: `{ descricao, valor, data (yyyy-MM-dd), veiculo: { id } | null }`
- `GET /despesas` — lista
- `DELETE /despesas/{id}` — remove despesa
- `GET /despesas/resumo-mensal` — [{ano, mes, valor}] (somatório de despesas por mês)
- `GET /financeiro/resumo-mensal` — [{ano, mes, receita, despesas, lucro}] (receita = soma de lucros das vendas)

