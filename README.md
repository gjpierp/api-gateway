# API Gateway - Ecosistema de Microservicios

Este microservicio actúa como la puerta de enlace centralizada (API Gateway) para el ecosistema de microservicios. Está diseñado bajo un enfoque reactivo utilizando Spring Cloud Gateway, permitiendo el enrutamiento dinámico de tráfico, el balanceo de carga en conjunto con Netflix Eureka Server, y la agregación automática de la documentación de Swagger OpenAPI para todos los microservicios del ecosistema.

## Características Principales

*   **Enrutamiento Reactivo**: Enruta las solicitudes entrantes a los microservicios correspondientes basándose en patrones de rutas definidos de forma declarativa.
*   **Descubrimiento y Balanceo de Carga**: Integrado con Spring Cloud Starter Netflix Eureka Client para la resolución dinámica de ubicaciones físicas de servicios y balanceo de carga del lado del cliente (`lb://`).
*   **Agregación de Swagger/OpenAPI Dinámica**: Configuración programática a través de tareas programadas (`@Scheduled`) para detectar servicios registrados en Eureka que cumplan con ciertos patrones de nombre (por ejemplo, terminados en `-service` o autodetectados mediante `CompositeDiscoveryClient_`) y exponer de forma centralizada sus especificaciones OpenAPI en una sola interfaz de Swagger UI.
*   **Preparado para Docker**: Incluye un Dockerfile de múltiples etapas (Multi-stage build) para optimizar la construcción de imágenes y scripts de arranque automatizados.

---

## Tecnologías Utilizadas

*   **Lenguaje de Programación**: Java 21 (Eclipse Temurin)
*   **Framework**: Spring Boot 3.5.15 & Spring Cloud 2025.0.0
*   **Módulo de Gateway**: Spring Cloud Gateway (Reactivo, basado en Netty)
*   **Registro de Servicios**: Netflix Eureka Client
*   **Documentación de API**: Springdoc OpenAPI Starter WebFlux UI 2.8.9
*   **Contenerización**: Docker & Docker Compose v3.8

---

## Estructura del Módulo

El proyecto sigue una estructura limpia dentro de sus componentes de configuración e inicialización de infraestructura:

```text
api-gateway/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── start.bat
├── start.ps1
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── gjpierp/
        │           └── gateway/
        │               ├── ApiGatewayApplication.java
        │               └── config/
        │                   └── OpenApiConfig.java
        └── resources/
            ├── application.yml
            └── public/
```

### Componentes Clave:
*   [ApiGatewayApplication.java](file:///c:/Local/api-gateway/src/main/java/com/gjpierp/gateway/ApiGatewayApplication.java): Clase principal de arranque del microservicio.
*   [OpenApiConfig.java](file:///c:/Local/api-gateway/src/main/java/com/gjpierp/gateway/config/OpenApiConfig.java): Bean de configuración con un scheduler fijo cada 30 segundos encargado de interrogar a Eureka sobre los microservicios activos, mapear sus rutas OpenAPI `/v3/api-docs` y poblar dinámicamente la configuración del Swagger UI de forma que no se requiera configuración estática para nuevos microservicios.
*   [application.yml](file:///c:/Local/api-gateway/src/main/resources/application.yml): Configuración del puerto del servidor (`8080`), credenciales/URL de Eureka Server y definición declarativa de rutas base estáticas.

---

## Configuración de Rutas Base

Actualmente, el archivo [application.yml](file:///c:/Local/api-gateway/src/main/resources/application.yml) expone las siguientes rutas clave:

1.  **Swagger del Backend Base**:
    *   **Predicado**: `/sitio-base-backend/v3/api-docs`
    *   **Destino**: `lb://sitio-base-backend`
    *   **Acción**: Reescribe la ruta para consultar `/v3/api-docs` de forma transparente en el microservicio destino.
2.  **API Funcional**:
    *   **Predicado**: `/api/**`
    *   **Destino**: `lb://sitio-base-backend`

---

## Requisitos Previos

Asegúrese de contar con los siguientes elementos instalados en su estación de trabajo:

*   **Java Development Kit (JDK)**: Versión 21
*   **Apache Maven**: Versión 3.9+ (opcional para desarrollo local sin Docker)
*   **Docker Desktop**: Ejecutándose en el sistema operativo local
*   **Netflix Eureka Server**: Debe estar levantado y accesible (por defecto en `http://eureka-server:8761/eureka/` dentro de la red docker).

---

## Despliegue y Ejecución

### Opción 1: Usando Docker Compose (Recomendado)

El microservicio está preparado para ejecutarse dentro de la red virtual global del ecosistema (`global-network`). 

Para levantar el servicio de forma automatizada (deteniendo instancias previas, reconstruyendo la imagen desde cero sin caché y levantando el contenedor en segundo plano con comprobación de estado activa), ejecute según su plataforma:

**En Windows (PowerShell):**
```powershell
.\start.ps1
```

**En Windows (Consola de comandos CMD):**
```cmd
start.bat
```

### Opción 2: Desarrollo Local

Si desea ejecutar el microservicio directamente desde su IDE o la consola local:

1.  Asegúrese de que el puerto `8080` esté libre en su máquina local.
2.  Ejecute la fase de compilación y empaquetado de Maven:
    ```bash
    mvn clean package -DskipTests
    ```
3.  Inicie la aplicación mediante Spring Boot Maven Plugin:
    ```bash
    mvn spring-boot:run
    ```

---

## Monitoreo y Pruebas

Una vez levantado el servicio, puede verificar su disponibilidad de las siguientes formas:

*   **Health Check**: El contenedor expone un monitoreo activo mediante `/actuator/health` en el puerto `8080`.
*   **Consola de Logs**:
    ```bash
    docker-compose logs -f api-gateway
    ```
*   **Acceso a Swagger UI Centralizado**:
    Abra un navegador web e ingrese a la dirección: `http://localhost:8080/swagger-ui.html`. Desde la lista desplegable superior derecha, podrá seleccionar e interactuar con la documentación interactiva de cada microservicio registrado en Eureka.
