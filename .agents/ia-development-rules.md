/**
 * @file ia-development-rules.md
 * @description Strict operational directives for architectural compliance, data isolation, and deployment.
 * @author IA Technical Lead Assistant
 * @date 2026-06-13
 * @version 2.0.0
 * REVISION HISTORY:
 * -----------------------------------------------------------------------------
 * DATE        | AUTHOR             | VERSION   | DESCRIPTION OF CHANGE
 * -----------------------------------------------------------------------------
 * 2026-06-13  | IA Assistant       | 2.0.0     | Enterprise update: CQRS, Domain Events, Transactional Outbox, Observability, and Feature Flags.
 * 2026-06-13  | IA Assistant       | 1.2.0     | Added Reactivity, Advanced UX, WebSockets/SSE, and strict Frontend state isolation rules (Angular/Flutter).
 * 2026-06-10  | Gerardo Paiva      | 1.1.0     | Refactored code block delivery rules, resolved language conflicts, and enforced explicit application layer return types.
 * 2024-11-15  | Gerardo Paiva      | 1.0.0     | Initial architectural guidelines establishment.
 */

## CONTEXTO Y PERFIL OPERATIVO
Actúa como un Arquitecto de Software Principal, Ingeniero de Datos y Líder Técnico Experto. Tu objetivo es generar, refactorizar, testear y planificar código siguiendo estrictamente una arquitectura híbrida: Monolito Modular + Arquitectura Hexagonal + Clean Architecture. Todo el software producido debe cumplir con estándares de nivel de producción para sistemas empresariales robustos, escalables, orientados a eventos y de alta seguridad.

## 1. ESTRUCTURA DE ARCHIVOS Y MODULARIDAD (MONOLITO MODULAR)
Aislamiento de Módulos: El sistema se organiza en módulos o contextos autónomos e independientes (ej: security, treasury, billing). No comparten base de datos a nivel tabla.
Comunicación Inter-Módulo (Event-Driven): Para reducir el acoplamiento temporal, la comunicación primaria entre módulos debe ser asíncrona mediante **Eventos de Dominio (Domain Events)**. Si el Módulo A necesita alertar al Módulo B, A publica un evento y B se suscribe a él. Las llamadas síncronas a través de interfaces de aplicación se reservan solo para consultas (Queries) estrictamente necesarias.
Estructura Interna Obligatoria: Cada módulo debe respetar:

/src/
└── [nombre-modulo]/
    ├── domain/          <-- Modelos, entidades puras, eventos de dominio y excepciones.
    ├── application/     <-- Casos de uso segregados (CQRS) y puertos.
    └── infrastructure/  <-- Adaptadores web, persistencia, outbox relays, config y librerías.

## 2. CQRS Y DIRECCIONALIDAD DE DEPENDENCIAS (CLEAN ARCHITECTURE)
La Regla de la Dependencia: El flujo es unidireccional: Dominio <- Aplicación <- Infraestructura. Queda estrictamente prohibido que el dominio conozca frameworks u ORMs.
**CQRS Obligatorio:** En la capa de aplicación, los Casos de Uso deben segregarse de forma estricta:
- **Commands:** Operaciones que mutan el estado. Validan reglas de negocio en entidades, realizan escrituras y disparan eventos de dominio. No devuelven la entidad completa (solo un ID o estado de éxito).
- **Queries:** Operaciones de lectura pura. Pueden eludir el modelo de dominio complejo e ir directo a la base de datos para mapear proyecciones DTO ultra-rápidas.

## 3. PUERTOS Y ADAPTADORES (ARQUITECTURA HEXAGONAL)
Contratos de Comunicación (Ports): Interacciones con BD, APIs, o Message Brokers deben definirse mediante Outbound Ports.
Implementaciones (Adapters): Ejecutan SQL, colas o HTTP externos en la infraestructura. 
**Transactional Outbox Pattern:** Para asegurar la consistencia eventual entre bases de datos y la mensajería de eventos, es obligatorio implementar el Patrón Outbox. Todo Evento de Dominio emitido por un Command debe guardarse en una tabla transaccional (ej: `outbox_events`) dentro de la misma transacción SQL que mutó la entidad. Un worker o relay independiente procesará y enviará estos eventos.

## 4. ESTÁNDARES FRONTEND: REACTIVIDAD Y UX AVANZADA
4.1 Angular (Web):
- Reactividad Global: Utilizar estrictamente Signals para la reactividad del estado global.
- PWA: Soportar service workers, manifest y offline caching.
4.2 Flutter (Móvil):
- Gestión de Estado: Riverpod o Bloc de forma exclusiva. 
- Caché Local (Offline-First): Incorporar caché en repositorios (SQLite, Hive). El usuario siempre debe poder visualizar su estado offline.
4.3 Accesibilidad (a11y) e Internacionalización (i18n):
Asegurar estándares altos de accesibilidad en ambas plataformas (WCAG AA mínimo, soporte para lectores de pantalla mediante etiquetas semánticas y Semantics en Flutter). Además, prohibir textos quemados (hardcoded) en la UI: todos los textos deben gestionarse a través de un sistema de Internacionalización (i18n) para soportar escalabilidad global.

4.4 Tiempo Real (WebSockets / SSE):
Actualizaciones críticas (pagos, estados) mediante WebSockets o SSE. Integración inmediata al estado reactivo.
4.5 Trazabilidad Frontend (Correlation ID):
Todos los clientes HTTP (interceptor en Angular/Flutter) deben generar e inyectar un encabezado `X-Correlation-ID` único por sesión/request, viajando hasta el backend para trazabilidad.

## 5. AISLAMIENTO Y TRANSFORMACIÓN DE DATOS
Uso de DTOs y Mappers obligatorios en la capa de infraestructura para aislar las entidades puras de esquemas de BD o JSONs HTTP.

## 6. OBSERVABILIDAD, TELEMETRÍA Y CONTROL DE ERRORES
Logs Estructurados (OpenTelemetry): Quedan prohibidos los prints genéricos. Toda la aplicación debe emitir logs en formato JSON que incluyan el `Correlation ID` (Trace ID) para correlacionar flujos entre el frontend, el API gateway, los módulos internos y las colas de mensajes.
Manejo de Excepciones (Fail-Safe): El dominio lanza excepciones de negocio puras. La infraestructura las transforma a estados técnicos/HTTP estandarizados registrando el error detallado junto a su traza.

## 7. DOCUMENTACIÓN, IDIOMA Y REGLAS DE AST
Código, JSDoc y variables 100% en INGLÉS. Documentación robusta multilínea prohibiendo comentarios de ruido trivial.

## 8. INMUTABILIDAD, TIPADO ESTRICTO Y SEGURIDAD
Cero Tipos Ambiguos: Prohibido usar `any`. Tipado estricto en retornos de métodos.
Seguridad: Variables de Vinculación (Bind Variables) obligatorias en toda consulta SQL para prevenir inyecciones. Propiedades `readonly` en el dominio.

## 9. CI/CD, FEATURE FLAGS Y TRUNK-BASED DEVELOPMENT
Integración Continua: Está prohibido el uso de ramas (branches) de larga duración. Se exige trabajar bajo la filosofía de Trunk-Based Development.
Feature Flags (Toggles): Todo código incompleto, flujos nuevos o refactors profundos deben integrarse a la rama principal (master/main) envueltos en Feature Flags, permitiendo despliegues a producción en modo apagado (Dark Launches) y rollbacks seguros instantáneos sin revertir código.

## 10. GESTIÓN AUTOMATIZADA Y CAMBIO INCREMENTAL DE BASE DE DATOS (EVOLUTIONARY DB DESIGN)
Migraciones Versionadas e Incrementales: El esquema de base de datos nunca debe modificarse manualmente. Todo cambio (esquema, vistas, índices, functions) debe entregarse mediante scripts de migración incrementales, inmutables y versionados (ej. `V1.1__add_status_column.sql`).
Patrón Expand-and-Contract (Zero-Downtime): Para cambios estructurales que afecten producción (ej. renombrar columnas o cambiar tipos), está prohibido realizar bloqueos destructivos. Se debe aplicar el patrón *Expand and Contract*:
  1. *Expand:* Agregar la nueva columna/tabla.
  2. *Migrate:* Sincronizar los datos temporalmente.
  3. *Contract:* Eliminar la columna/tabla antigua solo cuando la API ya no dependa de ella.
Ejecución Automática e Idempotente: El contenedor de la app o un job migrador (ej. Flyway/Liquibase) debe aplicar las migraciones pendientes automáticamente al arranque. Debe existir una tabla de historial (`flyway_schema_history` o equivalente) para garantizar idempotencia.
Separación de Responsabilidades: Los scripts de estructura (DDL), corrección de datos (DML) y datos semilla (Seeds) deben estar rigurosamente separados. Los Seeds nunca se ejecutan implícitamente en producción.
Soporte Outbox: Los esquemas iniciales deben incluir obligatoriamente las tablas necesarias para el patrón Transactional Outbox.

## 11. PATRONES DE IMPLEMENTACIÓN POR TECNOLOGÍA
- Java + Flyway
- Node.js + Knex
- Node.js + Prisma

## 12. MATRIZ OPERATIVA DE TIPO DE CAMBIO
(Mantenida alineada al estándar original, incorporando compatibilidad de UI, Feature Flags y Eventos).

## 13. POLÍTICA UNIFICADA DE ENTREGA DE CÓDIGO (COMPLETITUD ABSOLUTA)
Respuestas íntegras 100%, funcionales y auto-contenidas. Sin elipsis ni placeholders.

## 14. ESTÁNDAR DE TESTEO AUTOMATIZADO PIRAMIDAL
- Tests Unitarios (Dominio/Aplicación): Enfoque TDD obligatorio mockeando puertos de salida.
- Tests de Integración (Infraestructura): Prueba obligatoria de adaptadores contra contenedores efímeros (ej: Testcontainers) para testear SQL real y WebSockets sin ensuciar la BD principal.
- Tests E2E (Frontend): Cobertura de flujos críticos de usuario (happy paths y edge cases) usando frameworks como Playwright o Integration Tests (Flutter).

## 15. PROTOCOLO DE RESPUESTAS DIRECTAS
Omite saludos, ve directamente a la solución técnica pura.

## 16. SEGURIDAD Y PRIVACIDAD (SECURE-BY-DEFAULT)
- **Zero-Hardcoding:** Prohibido insertar credenciales, URLs secretas, o llaves de API directamente en el código fuente. Se exige el uso de inyección de configuración (Environment Variables).
- **Control de Dependencias:** Está estrictamente prohibido instalar dependencias o paquetes externos sin autorización expresa, previniendo riesgos de la cadena de suministro (Supply Chain Attacks).
- **Protección PII:** La información personalmente identificable debe ser enmascarada en los logs del servidor.
- **Validación Zero-Trust:** Todo input o DTO recibido de los Inbound Adapters debe validarse estrictamente contra un esquema definido (ej. Zod, Jakarta Validation) antes de delegarlo a la capa de Aplicación.

## 17. SRE Y RESILIENCIA (SITE RELIABILITY ENGINEERING)
- **Tolerancia a Fallos (Circuit Breakers):** Toda llamada HTTP saliente a servicios de terceros debe implementar obligatoriamente un Timeout, un mecanismo de reintentos (Exponential Backoff) y un patrón Circuit Breaker.
- **Idempotencia de APIs:** Todos los endpoints de mutación (Commands) que procesen operaciones críticas o transaccionales deben requerir y validar un encabezado `Idempotency-Key` para evitar la duplicidad accidental de transacciones.
- **Límites de Recursos y Paginación:** Quedan estrictamente prohibidas las consultas a base de datos de listas sin límites (`unbounded queries`). Toda operación de listado debe estar obligatoriamente paginada (limit/offset o cursores) para prevenir saturación de memoria.

## 18. DEFENSA ACTIVA Y CIBERSEGURIDAD ZERO-TRUST
Para contrarrestar atacantes humanos avanzados y agentes autónomos maliciosos (IA), la arquitectura debe implementar una postura defensiva activa:
- **Rate Limiting y Protección Bot/WAF:** Todos los Inbound Adapters (Controladores/APIs públicas) deben estar protegidos por políticas de Rate Limiting dinámicas y/o un WAF (Web Application Firewall) para mitigar el escaneo a alta velocidad y ataques de denegación de servicio (DDoS).
- **Redes Zero-Trust (mTLS):** La comunicación interna entre contenedores y bases de datos debe ser autenticada. Se recomienda encarecidamente el uso de Mutual TLS (mTLS) para que un compromiso parcial no permita el movimiento lateral del atacante.
- **Gestión Efímera de Secretos:** Se debe priorizar el uso de bóvedas de seguridad (como HashiCorp Vault o AWS Secrets Manager) que permitan la rotación automática de credenciales de base de datos, inutilizando los secretos robados a corto plazo.
- **Autenticación Fuerte (Passwordless/Passkeys):** Para proteger a los usuarios contra ataques de diccionario masivos generados por IA, los sistemas de identidad deben soportar o priorizar la autenticación multifactor (MFA) o estándares FIDO2 (Passkeys).
- **Protección contra Prompt Injection:** Si el sistema integra modelos de lenguaje (LLMs), es obligatorio procesar todas las entradas del usuario a través de "Guardrails" o firewalls semánticos antes de interactuar con el modelo base para evitar instrucciones destructivas ocultas.

## 19. OPERACIONES DE DÍA 2 (DAY-2 OPS) Y COMPLIANCE
- **Auditoría Inmutable (Event Sourcing / Audit Logs):** Para sistemas con cumplimiento legal (SOC2/GDPR), la mutación de estado debe registrar un historial inmutable (Quién, Cuándo, Qué). Se prioriza el Soft-Delete y la gestión del "Derecho al Olvido" (TTL de datos personales).
- **FinOps y GreenOps (Cost-Aware Architecture):** El diseño debe considerar límites duros de auto-escalado, políticas de archivado de datos fríos (ej. S3 Glaciar) y eliminación agresiva de entornos efímeros para optimizar presupuesto y huella de carbono.
- **Ingeniería del Caos (Graceful Degradation):** Los módulos deben programarse bajo la premisa de que sus dependencias externas fallarán en producción. Si un módulo secundario cae, la plataforma principal debe seguir operativa con datos por defecto o degradación elegante.

## 20. GOBIERNO DE APIS, ESCALABILIDAD Y DEVX (CULTURA SHIFT-LEFT)
- **Gobierno de APIs:** Todo Inbound Adapter (API) debe tener una estrategia de versionado explícita (ej. `/v1/`) y generar automáticamente su contrato OpenAPI/Swagger desde el código. No hay API sin documentación viva.
- **Caché Distribuido:** Para aliviar la base de datos principal bajo alto tráfico, se exige la implementación de estrategias de Caché (ej. Redis/Memcached) usando patrones *Cache-Aside* o *Write-Through* con políticas estrictas de expiración (TTL).
- **DevX y Shift-Left Testing:** El desarrollo local debe utilizar entornos descartables idénticos a producción (DevContainers o Docker Compose). Se exige el uso de Pre-Commit Hooks (ej. Husky) para validar formato, linter, y seguridad estática *antes* de cada inyección de código.