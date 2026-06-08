# Sistema de Conta Bancária com Controle de Concorrência utilizando Spring Boot e JPA/Hibernate

## Integrantes

* Rodrigo Loureiro
* Erick Andrew

### Divisão das Responsabilidades

**Parte 1 – Cenário sem Controle de Concorrência (Aluno A)**

Responsável: Rodrigo Loureiro

Implementações realizadas:

* Entidade `ContaBancaria`
* Operações de depósito e saque
* Transações utilizando apenas `@Transactional`
* Simulação de concorrência sem bloqueio
* Testes de carga utilizando Apache JMeter
* Análise do problema de Lost Update (Atualização Perdida)

**Parte 2 – Controle de Concorrência com Versionamento Otimista (Aluno B)**

Responsável: Erick Andrew C

Implementações realizadas:

* Entidade `ContaBancariaVersionada`
* Controle de versão com `@Version`
* Tratamento da exceção `ObjectOptimisticLockingFailureException`
* Retorno HTTP 409 (Conflict)
* Testes concorrentes utilizando Apache JMeter
* Comparação dos resultados com a Parte 1

---

# Objetivo

Compreender na prática os problemas de concorrência em sistemas transacionais e demonstrar a utilização do Controle de Concorrência Otimista (Optimistic Locking) através do JPA/Hibernate.

---

# Tecnologias Utilizadas

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* Banco de Dados H2
* Apache JMeter
* Maven

---

# Estrutura do Projeto

```text
src/main/java
│
├── controller
│   ├── ContaController
│   └── ContaVerController
│
├── service
│   ├── ContaBancariaService
│   └── ContaVersionadaService
│
├── repository
│   ├── ContaBancariaRepository
│   └── ContaVersionadaRepository
│
├── entity
│   ├── ContaBancaria
│   └── ContaBancariaVersionada
│
├── dto
│   └── ValorDTO
│
└── exception
    └── GlobalExceptionHandler
```

---

# Como Executar o Projeto

## Pré-requisitos

* Java 21 ou superior
* Maven
* Apache JMeter

## Executando

Clone o repositório:

```bash
git clone https://github.com/Rodrifer2004/conta-bancaria-ativ.git
```

Entre na pasta:

```bash
cd conta-bancaria
```

Execute:

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

---

# Banco de Dados H2

Console:

```text
http://localhost:8080/h2-console
```

Configuração padrão:

```text
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password:
```

---

# Endpoints

## Conta sem Controle de Concorrência

### Depósito

```http
POST /contas/{id}/deposito
```

Body:

```json
{
  "valor": 100.00
}
```

### Saque

```http
POST /contas/{id}/saque
```

Body:

```json
{
  "valor": 50.00
}
```

---

## Conta com Controle de Concorrência

### Depósito

```http
POST /contas-versionadas/{id}/deposito
```

Body:

```json
{
  "valor": 100.00
}
```

### Saque

```http
POST /contas-versionadas/{id}/saque
```

Body:

```json
{
  "valor": 50.00
}
```

---

# Testes de Concorrência

Cenário do teste

    Saldo inicial: R$ 1.000,00
    Operações: 500 depósitos de R$ 2,00
    Saldo final esperado: R$ 2.000,00


Foi utilizado o Apache JMeter para simular múltiplas requisições simultâneas sobre a mesma conta bancária.

Configuração utilizada:

```text
Threads (Users): 500
Ramp-Up: 1 segundo
Loop Count: 1
```

<img width="223" height="131" alt="threads de users2" src="https://github.com/user-attachments/assets/39a9bd86-0bc1-42d0-93b0-72327d75e05c" />


O arquivo do cenário utilizado encontra-se na raiz do projeto:

```text
Test Plan.jmx
```

---

# Relatório de Conclusão

## Parte 1 – Sem Controle de Concorrência

A entidade `ContaBancaria` não utiliza nenhum mecanismo de controle de versão.

Durante os testes concorrentes, múltiplas transações acessaram e atualizaram o mesmo registro simultaneamente.

Como consequência, ocorreu o fenômeno conhecido como Lost Update (Atualização Perdida), no qual uma atualização sobrescreve outra sem que o sistema detecte o conflito.

Resultado observado:

* Saldo final inconsistente.
* Valor final diferente do esperado.
* Perda de atualizações concorrentes.

Evidências:

<img width="463" height="267" alt="Conta bancaria" src="https://github.com/user-attachments/assets/228e5374-1a5c-4d40-bca2-6a8a48ed88a2" />
<br>
<img width="448" height="541" alt="sem erros2" src="https://github.com/user-attachments/assets/6fb19632-4403-492f-a15b-128391416553" />
<br>
<img width="230" height="299" alt="resultado obtido2" src="https://github.com/user-attachments/assets/221341ce-9b32-48f2-b331-278f58d7529c" />


---

## Parte 2 – Controle de Concorrência com @Version

Foi criada a entidade `ContaBancariaVersionada`, contendo o atributo:

```java
@Version
private Integer version;
```

O Hibernate passou a controlar automaticamente a versão do registro.

Quando duas transações tentaram atualizar simultaneamente a mesma conta, o framework detectou o conflito e lançou a exceção:

```text
ObjectOptimisticLockingFailureException
```

Essa exceção foi tratada através de um `@RestControllerAdvice`, retornando:

```http
409 Conflict
```

Resultado observado:

* Não houve atualização perdida.
* Os conflitos foram detectados corretamente.
* O sistema retornou erro controlado ao invés de sobrescrever dados.

Evidências:

<img width="456" height="293" alt="Conta versionada" src="https://github.com/user-attachments/assets/2a06b72b-0077-41cd-9a74-db33ffd52ef8" />
<br>
<img width="448" height="543" alt="erros2 409" src="https://github.com/user-attachments/assets/5541518a-0c14-4dcb-be30-1e589b0c250c" />
<br>
<img width="315" height="308" alt="resultado versionado obtido2" src="https://github.com/user-attachments/assets/49d40873-d172-492a-bfb8-6d28e7be4376" />

---

# Comparação dos Resultados

| Característica          | Sem @Version | Com @Version |
| ----------------------- | ------------ | ------------ |
| Atualização Perdida     | Sim          | Não          |
| Detecção de Conflito    | Não          | Sim          |
| Saldo Inconsistente     | Sim          | Não          |
| Exceção de Concorrência | Não          | Sim          |
| HTTP 409 Conflict       | Não          | Sim          |
| Integridade dos Dados   | Baixa        | Alta         |

---

# Conclusão

Os testes demonstraram que aplicações transacionais sujeitas a acessos simultâneos podem apresentar inconsistências quando não utilizam mecanismos de controle de concorrência.

A utilização do versionamento otimista através da anotação `@Version` permitiu detectar conflitos de atualização e preservar a integridade dos dados, evitando o problema de Lost Update.

Assim, o Controle de Concorrência Otimista mostrou-se uma solução eficaz para cenários com múltiplos acessos simultâneos ao mesmo registro.


