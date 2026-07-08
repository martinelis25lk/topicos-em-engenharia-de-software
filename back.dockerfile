# ── ETAPA 1: Compilação do Java (Precisa do JDK) ──────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copia os arquivos do Maven e baixa as dependências de forma otimizada
COPY car-service/mvnw car-service/pom.xml ./
COPY car-service/.mvn .mvn
RUN ./mvnw dependency:go-offline -q

# Copia o código fonte e gera o arquivo .jar
COPY car-service/src ./src
RUN ./mvnw package -DskipTests -q


# ── ETAPA 2: Runtime do Backend (Usa o JRE, mais leve) ────────────────────────
FROM eclipse-temurin:17-jre-alpine AS backend_stage

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

WORKDIR /app

# Copia o .jar gerado lá na ETAPA 1 (builder)
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx350m", "-Xms200m", "-jar", "app.jar"]


# ── ETAPA 3: Runtime do Frontend (Nginx) ──────────────────────────────────────
FROM nginx:alpine AS frontend_stage

# Copia a pasta 'dist' que você buildou manualmente na máquina
COPY frontend/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]