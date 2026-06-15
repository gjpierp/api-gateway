# PROTOCOLO OPERATIVO DE GESTIÓN DE CAMBIOS (ENTERPRISE EDITION)

1. Identificar el tipo de proyecto y stack dominante:
    - Java + Spring / Flyway o Liquibase
    - Node.js + Knex / Prisma
    - Frontend con estado reactivo (Angular+Signals / Flutter+Riverpod)
    - Servicio con mensajería (EDA / Domain Events) o jobs batch

2. Clasificar el cambio solicitado:
    - Base de datos (Evolutiva / Expand-and-Contract)
    - API / Contrato (Command vs Query - CQRS)
    - Eventos de Dominio (Outbox Pattern)
    - Configuración y Trazabilidad (OpenTelemetry / Correlation ID)
    - Infraestructura Docker
    - Feature Flags / Despliegues

3. Exigir artefactos mínimos según clasificación:
    - Si cambia la BD: Crear migración versionada incremental (NUNCA cambios destructivos de golpe) y asegurar tablas Outbox si aplica.
    - Si cambia API: Actualizar DTOs, definir explícitamente si es un Command o Query, y mantener compatibilidad hacia atrás.
    - Si cambia mensajería/eventos: Crear el Evento de Dominio y asegurar su registro transaccional vía Outbox Pattern.
    - Si cambia configuración: Actualizar variables, .env.example y asegurar propagación del Correlation ID.
    - Si es una funcionalidad nueva o refactor crítico: Integrar un Feature Flag (Dark Launch).

4. Validar impacto transversal:
    - Compatibilidad hacia atrás y Zero-Downtime.
    - Trunk-Based Development (sin ramas largas).
    - Idempotencia y Fail-fast en inicialización de servicios/migraciones.

5. Entregar siempre, cuando aplique:
    - Código (limpio, modularizado y desacoplado).
    - Migración de BD o script de estado asociado.
    - Pruebas Piramidales: Unitaria (Mocks), de Integración (Testcontainers) o E2E (Playwright/Flutter).
    - Instrucción de ejecución dentro de Docker o Feature Flag a habilitar.

---

**NOTA ADICIONAL ENTERPRISE:** Este proyecto implementa un Monolito Modular Orientado a Eventos con Arquitectura Hexagonal y CQRS. Todo código debe generarse en INGLÉS, pero la documentación multilínea en ESPAÑOL técnico (ver `ia-development-rules.md`). 
Queda terminantemente prohibido:
1. Saltarse el encapsulamiento de módulos (acceso directo a código ajeno).
2. Romper la segregación CQRS (mezclar lecturas complejas en mutaciones).
3. Publicar eventos sin transaccionalidad atómica (ignorar el Outbox).
4. Silenciar errores o evadir el paso del Trace ID/Correlation ID.
