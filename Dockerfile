# /*
#  @file Dockerfile
#  @description Dockerfile multi-etapa para construir y ejecutar el API Gateway con JDK 21
#  @author Gerado Paiva
#  @date 2026-06-04
#  @version 1.0.0
#  HISTORIAL DE CAMBIOS:
#  -----------------------------------------------------------------------------
#  FECHA        | AUTOR             | VERSIÓN   | DESCRIPCIÓN DEL CAMBIO
#  -----------------------------------------------------------------------------
#  2026-06-04   | Gerardo Paiva     | 1.0.0     | Creación inicial del archivo.
#  */

FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
