# LasanhaSpec
Um projeto mais intimista, mas que não deixa de ser ambicioso para todos aqueles que gostam de carros ou que querem aprender mais sobre eles.

É uma plataforma voltada para entusiastas do mundo automotivo, das corridas e da preparação. 
Com foco em análise técnica de veículos, preparação automotiva e impacto de modificações de performance, consumo e custo, utilizando arquitetura baseada em microserviços.

O projeto nasceu da ideia do autor de sempre pensar nos carros como grandes legos alinhado ao prazer e curiosidade em programar coisas diferentes.
E como todo bom grande lasanheiro, quem não gostaria de um programa acessível de análise de performance baseado em valores e impactos reais que as modificações proporcionam para o seu possante, hein? haha

🚗 Sobre o Projeto

Lasanha Spec é uma plataforma voltada para entusiastas de carros usados e modificados, com foco inicial no mercado brasileiro.

O objetivo é centralizar conhecimento técnico, experiências reais e projetos automotivos em um único ambiente acessível, estruturado e confiável aplicar conceitos de:
- Arquitetura de microserviços
- Spring Boot e JAVA
- APIs REST
- Docker e CI/CD
- Cloud (AWS)





Andei me informando sobre microserviços e percebi que não é todo projeto que se deve começar de cara com esse tipo de arquitetura de software, e que em muitos casos há a necessidade de maturar o projeto em um
monolitico bem estruturado e de acordo com a escalabilidade e necessidades do projeto, ir quebrando o sistema para uma nova concepção, mas como este projeto é mais como uma cobaia para fins didáticos e de diversão mesmo. Então o projeto segue uma abordagem baseada em microserviços, iniciando com um serviço central de veículos.

graph TD
    Controller --> Service
    Service --> Repository
    Repository --> Database
    Service --> SetupRulesService
    UserVehicle --> CurrentSetup
    CurrentSetup --> CommunitySetup




## 🇧🇷 Problema

O Brasil possui uma frota de veículos de passeio cada vez mais envelhecida, impulsionada pelo alto custo dos carros novos desde a pandemia.

Consequências:

* Crescente dependência de veículos antigos
* Manutenções complexas e caras
* Falhas recorrentes pouco documentadas
* Informação dispersa em fóruns e vídeos
* Alto risco para proprietários e compradores

Entusiastas frequentemente dependem de conhecimento informal para evitar prejuízos graves.

---

## 💡 Proposta da Plataforma

Criar um hub que combine:

* Base de conhecimento técnica
* Rede social de projetos automotivos
* Garagem digital do usuário
* Documentação de problemas crônicos
* Compartilhamento de builds e modificações

---

## 👥 Público-Alvo

### 🧑‍🔧 Entusiastas hardcore

* Realizam modificações profundas
* Buscam dados técnicos
* Compartilham projetos

### 🚘 Donos apaixonados

* Gostam de um modelo específico
* Querem aprender mais
* Podem fazer upgrades leves

### 👀 Usuários curiosos ou compradores

* Pesquisam antes de adquirir um veículo
* Precisam de informações rápidas e confiáveis

---

## ⭐ Funcionalidades do MVP

### 🚗 Garagem do Usuário

* Cadastro de veículos próprios
* Informações básicas e personalização
* Ativação/desativação

---

### 🛠️ Setups / Builds

* Modificações atuais do veículo
* Versionamento de configurações
* Estatísticas básicas

---

### 📷 Imagens do Veículo

* Upload para armazenamento na nuvem
* Múltiplas fotos por veículo
* Exibição organizada

---

### ⚠️ Crônicos por Modelo

Base colaborativa de problemas recorrentes:

* Descrição do problema
* Sintomas
* Severidade
* Manutenção preventiva
* Estimativa de custo
* Votos de utilidade

---

### ❤️ Interação Social Básica

* Curtidas em fotos e builds
* Feed simples de atualizações

---

## 🏗️ Arquitetura

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* Banco relacional
* AWS S3 para imagens

### Frontend (em desenvolvimento)

* React
* TypeScript
* Vite

---

## 🎯 Objetivo do MVP

Validar a utilidade da plataforma e construir uma base sólida para evolução futura, evitando complexidade prematura.

---

## 🚀 Status do Projeto

Em desenvolvimento ativo.

Funcionalidades core do backend já implementadas ou em progresso, incluindo:

* Modelagem de domínio
* Estrutura de APIs REST
* Integração com storage na nuvem

---

## 📌 Visão de Longo Prazo

Transformar a plataforma em referência para:

* Conhecimento técnico automotivo colaborativo
* Comunidade de entusiastas
* Documentação de builds reais
* Prevenção de problemas mecânicos comuns

---

## 👨‍💻 Autor

Projeto desenvolvido como iniciativa independente com foco em aprendizado prático e potencial de produto real.

---



