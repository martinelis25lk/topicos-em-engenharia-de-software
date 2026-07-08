FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# 📁 Busca o Maven wrapper e o pom.xml de dentro de car-service
COPY car-service/mvnw car-service/pom.xml ./
COPY car-service/.mvn .mvn

RUN ./mvnw dependency:go-offline -q

# 📁 Busca o código fonte de dentro de car-service
COPY car-service/src ./src
RUN ./mvnw package -DskipTests -q

# ── Runtime stage (Backend) ───────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx350m", "-Xms200m", "-jar", "app.jar"]


# ── Runtime stage (Frontend) ──────────────────────────────────────────────────
# 💡 O "AS frontend_stage" permite que o docker-compose isole este pedaço
FROM nginx:alpine AS frontend_stage

# Copia a pasta gerada pelo build do seu front (ajuste se for 'dist' ou 'build')
COPY frontend/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]