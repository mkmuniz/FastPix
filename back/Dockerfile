# Estágio de build
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio final
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Variáveis de ambiente para configuração da JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Expõe a porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 