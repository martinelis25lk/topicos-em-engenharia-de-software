# LasanhaSpec

LasanhaSpec é um hub automotivo feito para entusiastas de carros, com foco em análise técnica, garagem digital e registro colaborativo de problemas recorrentes (crônicos).

A proposta não é apenas ser um catálogo de veículos, mas centralizar informações reais de uso, manutenção, modificações e experiências de proprietários.

---

# 2ª Avaliação — Protótipo 1 (Disciplina)

Este documento também serve como mapeamento direto dos requisitos da avaliação.

---

## A — Funcionalidades da aplicação web

### 1. Controle de autenticação e autorização (Login e Registro)

O sistema possui autenticação completa baseada em JWT.

- Registro de usuário
  - `POST /auth/register`
  - validação de email único
  - senha criptografada com BCrypt
  - retorno de token JWT

- Login
  - `POST /auth/login`
  - autenticação via Spring Security
  - geração de JWT

- Segurança
  - `JwtAuthFilter` intercepta requisições
  - `SecurityContext` mantém usuário autenticado

No frontend:
- token armazenado no cliente
- `ProtectedRoute` bloqueia acesso a rotas protegidas

---

### 2. Dashboard do usuário autenticado

Após login, o usuário acessa a área autenticada.

- `DashboardPage.tsx` já existe, mas ainda está em construção
- layout principal com menu lateral funcional
- rotas já implementadas:
  - garagem
  - catálogo
  - crônicos
  - perfil

O dashboard ainda precisa de finalização visual e funcional.

---

### 3. Edição de perfil do usuário

Funcionalidade implementada:

- `PATCH /users/me`
- `UserController`

No frontend:
- página de perfil
- página de configurações da conta

---

### 4. Upload de imagem de perfil

Implementado com integração AWS S3:

- `POST /users/me/profile-image`
- serviço `S3StorageService`
- URL da imagem salva no campo `profileImageUrl`

---

### 5. Propriedades do usuário

A entidade User contém:

- id
- nome completo
- username
- email
- senha (hash)
- imagem de perfil
- descrição
- data de criação
- role

---

### 6. Alteração de senha

Funcionalidade ainda não implementada como endpoint separado.

---

# B — Infraestrutura AWS

## AWS S3

Em uso para upload de imagens de usuários e veículos.

---

## Infraestrutura não documentada

Ainda não há evidência completa dos seguintes itens:

- VPC
- EC2
- Security Groups
- tabela de rotas
- IP público

O projeto atualmente roda em ambiente local com docker-compose.

---

# C — Repositório e documentação

## Repositório Git

Projeto versionado com estrutura organizada:

- car-service
- market-service
- frontend
- docs

---

## Documentação existente

- chronic-issues-module.md
- profile-image-feature.md
- Domain-decisions.md

---

## Documentação pendente

- aws-deploy.md (ainda não criado)

---

# Fluxo de autenticação

Registro de usuário gera JWT  
Login autentica e retorna JWT  
Frontend armazena token  
Backend valida via JwtAuthFilter  

---

# Entidade User

- id
- nome completo
- username
- email
- senha (hash)
- imagem de perfil
- descrição
- data de criação
- role

---

# Arquitetura

O sistema começou como monolito e está evoluindo para uma arquitetura baseada em serviços.

## car-service
Responsável pelo domínio principal:
- usuários
- veículos
- garagem
- crônicos
- uploads

## market-service
Responsável por dados externos:
- integração com FIPE
- dados de mercado
- evolução futura para peças e preços

---

# Fluxo geral

Frontend → car-service → market-service → APIs externas (FIPE)

---

# Infraestrutura AWS (resumo)

- S3 em uso
- EC2 pendente de deploy/documentação
- VPC pendente
- Security Groups pendente

---

# Checklist da avaliação

- Login e registro implementados
- JWT funcionando
- Edição de perfil implementada
- Upload de imagem funcionando
- Repositório Git estruturado
- Documentação parcial existente
- Dashboard ainda em construção
- Infraestrutura AWS ainda não evidenciada completamente

---

# Sobre o projeto

O problema abordado é a dificuldade de centralizar informações confiáveis sobre veículos antigos no Brasil.

Hoje essas informações estão dispersas em fóruns, vídeos e comunidades informais.

---

# Proposta

Criar uma plataforma que centraliza:

- garagem digital
- histórico de modificações
- problemas recorrentes por modelo
- análise técnica de veículos
- dados de mercado

---

# Funcionalidades principais

## Garagem digital
- cadastro de veículos
- fotos
- status do veículo

## Crônicos
- problemas recorrentes por modelo
- sistema de votos
- severidade e impacto

## Setups
- modificações do veículo
- histórico de evolução

## Imagens
- upload via S3
- galeria por veículo

---

# Arquitetura simplificada

```mermaid
graph TD

Frontend --> CarService
CarService --> MarketService
MarketService --> FIPE
