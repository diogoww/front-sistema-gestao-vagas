# 🌟 Frontend Sistema de Gestão de Vagas

Este repositório é o frontend do projeto de gestão de vagas. É uma aplicação web em Java com Spring Boot e Thymeleaf para gerenciamento de vagas, empresas e candidatos. O objetivo principal é prover uma interface web simples para candidatos se cadastrarem, visualizarem vagas e se candidatarem, além de empresas criarem contas e gerenciarem suas vagas.

> ⚠️ Backend necessário: a aplicação consome uma API externa. O repositório do backend está em `sistema-gestap-vagas` e deve estar em execução (por padrão em `http://localhost:8080`).

---

## 🧰 Tecnologias Utilizadas

- **Java 17**: Linguagem principal do projeto.
- **Spring Boot 3.2**: Framework para criação da aplicação web e gestão do ciclo de vida.
- **Spring Web**: Criação de controladores HTTP (MVC) e endpoints.
- **Thymeleaf**: Template engine para renderização de páginas HTML no servidor.
- **Spring Security**: Configuração de segurança e controle de acesso.
- **Thymeleaf Extras Spring Security 6**: Integração de tags de segurança do Spring no Thymeleaf.
- **Lombok**: Redução de boilerplate (getters, setters, builders) em classes Java.
- **Spring Boot DevTools**: Reload automático durante o desenvolvimento.
- **Spring Boot Test**: Suporte a testes automatizados.
- **Maven**: Gerenciador de dependências e build.

---

## 🛠️ Instalação e Configuração

### 1) Clonar os repositórios

- Frontend (este repositório):
```bash
git clone https://github.com/<seu-usuario>/frontend-sistema-gestao-vagas.git
cd frontend-sistema-gestao-vagas
```

- Backend (API necessária):
```bash
git clone https://github.com/<seu-usuario>/sistema-gestap-vagas.git
cd sistema-gestap-vagas
# Siga o README do backend para executar a API (porta padrão 8080)
```

### 2) Pré-requisitos
- Java 17 (JDK 17)
- Maven 3.9+ (ou wrapper do Maven)
- Porta 8082 livre (por padrão a aplicação sobe nela)
- Backend em execução (repositório: `sistema-gestap-vagas`) acessível em `http://localhost:8080` (ou conforme configurado)

### 3) Configuração
Variáveis e propriedades padrão estão em `src/main/resources/application.properties`:
```properties
server.port=8082
host.api.gestao.vagas=http://localhost:8080
```
Você pode sobrescrever em tempo de execução com propriedades do sistema:
```bash
# Exemplo: mudar porta e URL da API
java -Dserver.port=9090 -Dhost.api.gestao.vagas=https://api.seudominio.com \
  -jar target/frontend-sistema-gestao-vagas-1.0.jar
```

---

## ▶️ Como Executar o Projeto

### Ambiente de Desenvolvimento (frontend)
- Com hot reload (DevTools):
```bash
mvn spring-boot:run
```

- Executando testes (opcional):
```bash
mvn test
```

### Backend (API)
- Certifique-se de que o backend `sistema-gestap-vagas` esteja rodando antes de usar o frontend. Caso a API rode em outra URL/porta, ajuste `host.api.gestao.vagas`.

### Build de Produção (frontend)
- Gerar artefato:
```bash
mvn clean package -DskipTests
```

- Executar o JAR gerado:
```bash
java -jar target/frontend-sistema-gestao-vagas-1.0.jar
```

- Ajustar configurações em linha de comando (opcional):
```bash
java -Dserver.port=8082 -Dhost.api.gestao.vagas=http://localhost:8080 \
  -jar target/frontend-sistema-gestao-vagas-1.0.jar
```

---

## 🗂️ Estrutura de Pastas (Mapa do Projeto)

```text
frontend-sistema-gestao-vagas/
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/br/com/diogow/
│  │  │  ├─ FrontGestaoVagasApplication.java           # Classe principal Spring Boot
│  │  │  ├─ modules/
│  │  │  │  ├─ candidate/                              # Módulo de candidatos
│  │  │  │  │  ├─ controller/CandidateController.java  # Rotas de páginas e ações de candidato
│  │  │  │  │  ├─ dto/                                 # DTOs de entrada/saída
│  │  │  │  │  └─ service/                             # Regras de negócio (aplicar vaga, perfil, etc.)
│  │  │  │  └─ company/                                # Módulo de empresas
│  │  │  │     ├─ CompanyController.java               # Rotas de páginas e ações de empresa
│  │  │  │     ├─ dto/                                 # DTOs de empresa e vagas
│  │  │  │     └─ service/                             # Serviços (criar empresa, vagas, login, listar vagas)
│  │  │  ├─ security/SecurityConfig.java               # Configurações de segurança
│  │  │  └─ utils/FormatErrorMessage.java              # Utilitário de formatação de erros
│  │  ├─ resources/
│  │  │  ├─ application.properties                     # Configurações da aplicação (porta, API)
│  │  │  ├─ templates/                                 # Páginas Thymeleaf
│  │  │  │  ├─ candidate/ {create, login, profile, jobs}.html
│  │  │  │  └─ company/ {create, login, list, jobs}.html
│  │  │  └─ static/                                    # Arquivos estáticos (CSS/JS/imagens)
│  └─ test/
└─ target/                                             # Artefatos de build
```

### Principais componentes
- `FrontGestaoVagasApplication.java`: ponto de entrada da aplicação Spring Boot.
- `modules/*/controller`: controladores para navegação e ações de usuário.
- `modules/*/service`: comunicação com a API e regras de negócio do front.
- `modules/*/dto`: modelos de transferência de dados entre camadas.
- `templates/*`: páginas HTML renderizadas no servidor com Thymeleaf.
- `security/SecurityConfig.java`: regras de autenticação/autorização do front.
- `application.properties`: configurações (porta, host da API, etc.).

---

## ✨ Funcionalidades Principais

- **Cadastro e login de candidatos**: criação de contas e autenticação.
- **Visualização de vagas**: listagem e detalhamento de vagas disponíveis.
- **Candidatura a vagas**: envio de candidatura pelo candidato autenticado.
- **Perfil do candidato**: visualização e gerenciamento de informações do perfil.
- **Cadastro e login de empresas**: criação de contas corporativas e autenticação.
- **Criação de vagas**: empresas podem registrar novas oportunidades.
- **Listagem de vagas da empresa**: visão consolidada das vagas criadas.
- **Integração com API externa**: consumo da API definida em `host.api.gestao.vagas`.
- **Segurança**: integração com Spring Security/Thymeleaf para controle de acesso.

---

## 🔗 Repositório do Backend

- Nome do repositório: `sistema-gestap-vagas`
- Função: expor a API de domínio (autenticação, vagas, empresas, candidaturas).
- Requisitos: estar rodando e acessível via `host.api.gestao.vagas` configurado no frontend.

---

## 🧭 Boas Práticas / Convenções

- **Padrões de Código**: seguir convenções Java (nomes claros, classes coesas, métodos curtos).
- **Lombok**: usar para reduzir boilerplate, mantendo legibilidade.
- **Camadas**: separar responsabilidades em `controller`, `service`, `dto` e `templates`.
- **Commits**: recomenda-se Conventional Commits (`feat:`, `fix:`, `chore:`, `docs:`...).
- **Branches**: fluxo sugerido `main` (estável) e `feat/*` para novas funcionalidades.
- **Configuração via propriedades**: preferir propriedades (`application.properties`) ou `-D` no runtime.
- **Segurança**: não expor segredos no repositório; usar variáveis de ambiente quando necessário.

---

## 🙋 Suporte e Contato

Em caso de dúvidas ou sugestões, abra uma issue ou entre em contato com o mantenedor do projeto.
