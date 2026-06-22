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
Actúa como un Arquitecto de Software Principal, Ingeniero de Datos y Líder Técnico Experto. Tu objetivo es generar, refactorizar, testear y planificar código adaptándote dinámicamente al patrón arquitectónico definido para el proyecto (por defecto o como referencia: Monolito Modular + Arquitectura Hexagonal + Clean Architecture, pero pudiendo adoptar Microservicios, Event-Driven, Serverless u otros según se especifique en el archivo `CONTEXT.md` o en los Architectural Decision Records (ADRs) de la raíz). Todo el software producido debe cumplir con estándares de nivel de producción para sistemas empresariales robustos, escalables, orientados a eventos y de alta seguridad.

## 1. ESQUELETO INMUTABLE DE DIRECTORIOS Y MODULARIDAD
**Skeleton Base:** Sin importar la tecnología o el tamaño del proyecto, el directorio raíz DEBE respetar esta estructura inmutable para facilitar la navegación cruzada entre los 11 repositorios:
- `/docs` (ADRs, C4 Model, OpenAPI, Roadmap, Handoffs)
- `/src` (Código fuente de la aplicación)
- `/infra` (Terraform, Dockerfiles, manifiestos K8s)
- `/tests` (Unit, E2E, Load, Chaos)
- `/scripts` (Automatizaciones locales)

**Automatización del link del Agente Zero:** Todo script de inicio o inicialización del proyecto para desarrolladores (ej. `/scripts/setup-dev.ps1` o equivalente) debe verificar automáticamente la existencia del directorio `.agents` en la raíz del proyecto. Si no existe, debe crear de manera autónoma un Enlace Simbólico (Junction en Windows) apuntando a `C:\Local\.agents`.

Aislamiento de Módulos: Dentro de `/src`, el sistema se organiza en módulos o contextos autónomos e independientes (ej: security, treasury, billing). No comparten base de datos a nivel tabla.
Comunicación Inter-Módulo (Event-Driven): Para reducir el acoplamiento temporal, la comunicación primaria entre módulos debe ser asíncrona mediante **Eventos de Dominio (Domain Events)**. Si el Módulo A necesita alertar al Módulo B, A publica un evento y B se suscribe a él.
Estructura Interna Obligatoria: Cada módulo dentro de `/src` debe respetar:

/src/
└── [nombre-modulo]/
    ├── domain/          <-- Modelos, entidades puras, eventos de dominio y excepciones.
    ├── application/     <-- Casos de uso segregados (CQRS) y puertos.
    └── infrastructure/  <-- Adaptadores web, persistencia, outbox relays, config y librerías.

*(Nota: Esta estructura modular y de capas representa el estándar de referencia. Si el archivo CONTEXT.md o los ADRs definen una arquitectura alternativa como Microservicios independientes, Serverless por funciones o tuberías de procesamiento, el diseño de directorios y los flujos de importación de código deben adaptarse a dicha especificación, priorizando siempre el aislamiento de responsabilidades y la inversión de dependencias).*

## 2. CQRS Y DIRECCIONALIDAD DE DEPENDENCIAS (CLEAN ARCHITECTURE)
La Regla de la Dependencia: El flujo es unidireccional: Dominio <- Aplicación <- Infraestructura. Queda estrictamente prohibido que el dominio conozca frameworks u ORMs.
**CQRS Obligatorio:** En la capa de aplicación, los Casos de Uso deben segregarse de forma estricta:
- **Commands:** Operaciones que mutan el estado. Validan reglas de negocio en entidades, realizan escrituras y disparan eventos de dominio. No devuelven la entidad completa (solo un ID o estado de éxito).
- **Queries:** Operaciones de lectura pura. Pueden eludir el modelo de dominio complejo e ir directo a la base de datos para mapear proyecciones DTO ultra-rápidas.
- **Dominio Libre de Efectos Secundarios (Side-Effect-Free Pure Domain):** En las capas de dominio (entidades, agregados y value objects) y aplicación (casos de uso), el código debe estructurarse como funciones puras libres de efectos secundarios de E/S. Queda estrictamente prohibido realizar llamadas HTTP, interactuar con base de datos, leer archivos, consultar variables de entorno o invocar APIs de fecha/hora del sistema directamente en estas capas. Toda interacción con el mundo exterior debe definirse formalmente mediante Puertos (interfaces) y resolverse en los adaptadores correspondientes, permitiendo que la lógica de negocio pura se ejecute de forma 100% determinista y aislada en los tests.

## 3. PUERTOS Y ADAPTADORES (ARQUITECTURA HEXAGONAL)
Contratos de Comunicación (Ports): Interacciones con BD, APIs, o Message Brokers deben definirse mediante Outbound Ports.
Implementaciones (Adapters): Ejecutan SQL, colas o HTTP externos en la infraestructura. 
**Transactional Outbox Pattern:** Para asegurar la consistencia eventual entre bases de datos y la mensajería de eventos, es obligatorio implementar el Patrón Outbox. Todo Evento de Dominio emitido por un Command debe guardarse en una tabla transaccional (ej: `outbox_events`) dentro de la misma transacción SQL que mutó la entidad. Un worker o relay independiente procesará y enviará estos eventos.

## 4. ESTÁNDARES FRONTEND: REACTIVIDAD Y UX AVANZADA
4.1 Angular (Web):
- Reactividad Global: Utilizar estrictamente Signals para la reactividad del estado global.
- PWA: Soportar service workers, manifest y offline caching.
4.2 Flutter (Móvil) y PWA:
- Gestión de Estado: Riverpod o Bloc de forma exclusiva. 
- Caché Local (Offline-First Indestructible): Si el Backend colapsa totalmente, el Frontend debe seguir funcionando. Es obligatorio implementar IndexedDB o Hive. Las peticiones de escritura deben guardarse localmente y usar *Background Sync* (Service Workers) para enviarse automáticamente cuando el servidor reviva. Queda prohibido mostrar un error fatal de desconexión.
4.3 Accesibilidad (a11y) e Internacionalización (i18n):
Asegurar estándares altos de accesibilidad en ambas plataformas (WCAG AA mínimo, soporte para lectores de pantalla mediante etiquetas semánticas y Semantics en Flutter). Además, prohibir textos quemados (hardcoded) en la UI: todos los textos deben gestionarse a través de un sistema de Internacionalización (i18n) para soportar escalabilidad global.

4.4 Tiempo Real (WebSockets / SSE):
Actualizaciones críticas (pagos, estados) mediante WebSockets o SSE. Integración inmediata al estado reactivo.
4.5 Trazabilidad Frontend (Correlation ID):
Todos los clientes HTTP (interceptor en Angular/Flutter) deben generar e inyectar un encabezado `X-Correlation-ID` único por sesión/request, viajando hasta el backend para trazabilidad.

## 5. AISLAMIENTO Y TRANSFORMACIÓN DE DATOS
Uso de DTOs y Mappers obligatorios en la capa de infraestructura para aislar las entidades puras de esquemas de BD o JSONs HTTP.

## 6. OBSERVABILIDAD, TELEMETRÍA Y ESTÁNDAR GLOBAL DE ERRORES (RFC 7807)
Logs Estructurados (OpenTelemetry): Quedan prohibidos los prints genéricos. Toda la aplicación debe emitir logs en formato JSON que incluyan el `Correlation ID` (Trace ID) para correlacionar flujos entre el frontend, el API gateway, los módulos internos y las colas de mensajes.
**Enmascaramiento y Sanitización de Stacktraces:** Los logs estructurados en producción nunca deben contener stacktraces crudos (especialmente los arrojados por drivers de bases de datos u ORMs que exponen sentencias SQL y parámetros sensibles). Se exige interceptar los errores de infraestructura y formatearlos de manera que solo se registre información de contexto técnico seguro de alto nivel, asociándolo al `Correlation ID` correspondiente. Los detalles crudos de la pila de llamadas deben almacenarse de forma aislada y segura fuera del flujo de logs estándar.
Manejo de Excepciones (RFC 7807): Queda estrictamente prohibido que la IA invente formatos JSON de error a medida. Todas las APIs deben interceptar errores y devolverlos según el estándar **Problem Details for HTTP APIs (RFC 7807)** (conteniendo `type`, `title`, `status`, `detail` e `instance`). El `Trace ID` debe incluirse siempre en el cuerpo del error para debuggin.

## 7. DOCUMENTACIÓN, IDIOMA Y REGLAS DE AST
Código, JSDoc y variables 100% en INGLÉS. Documentación robusta multilínea prohibiendo comentarios de ruido trivial.

## 8. INMUTABILIDAD, TIPADO ESTRICTO Y SEGURIDAD
Cero Tipos Ambiguos: Prohibido usar `any`. Tipado estricto en retornos de métodos.
Seguridad: Variables de Vinculación (Bind Variables) obligatorias en toda consulta SQL para prevenir inyecciones. Propiedades `readonly` en el dominio.

## 9. CI/CD, CONVENTIONAL COMMITS Y GIT FLOW PERSONALIZADO
Flujo Git Estructurado (Multi-Rama): Se exige abandonar el Trunk-Based Development en favor de un flujo de integración estructurado que garantice el paso seguro del código por entornos de prueba. El ciclo de vida de las ramas debe gestionarse estrictamente de la siguiente manera:
- `main`: Rama principal de producción, inmutable y siempre estable (sustituye a `master`).
- `release_xxx`: Rama para empaquetado, pruebas pre-producción y estabilización de versiones (ej. `release_v1.2.0`). Nace desde `test` y se fusiona a `main`.
- `test`: Rama para despliegue en entornos de QA e Integración Continua (CI). Todo código aquí debe estar listo para revisión funcional.
- `dev`: Rama base de desarrollo diario e integración de características.
- `feature_xxx`: Ramas de desarrollo de nuevas características (ej. `feature_login`). Nacen SIEMPRE desde `dev` y se integran de vuelta a `dev` mediante Pull Requests.
- `hotfix_xxx`: Ramas para corrección de errores críticos. Nacen desde `main` y se fusionan a `main`, `test` y `dev`.

Reglas de Propagación y PRs: Queda **ESTRICTAMENTE PROHIBIDO** hacer `commit` directo a cualquier rama de integración (`main`, `release_xxx`, `test`, `dev`). La IA debe crear ramas temporales (como `feature_xxx`) y simular flujos de *Pull Request* para code review hacia `dev`, respetando el ciclo de propagación ascendente: `feature` -> `dev` -> `test` -> `release` -> `main`.
Conventional Commits en Español: Todos los mensajes de commit generados automáticamente por la IA deben usar el formato **Conventional Commits** redactados estrictamente en español técnico (ej. `feat(api): agregar autenticacion jwt`, `fix(db): corregir indice de busqueda`, `chore: actualizar dependencias`), a pesar de que el código base y sus variables se mantengan en inglés.
Feature Flags (Toggles): Todo código incompleto, flujos nuevos o refactors profundos deben integrarse a la rama principal (master/main) envueltos en Feature Flags, permitiendo despliegues a producción en modo apagado (Dark Launches) y rollbacks seguros instantáneos sin revertir código.
**Limpieza Obligatoria de Feature Flags Muertos (Flag Debt Pruning):** Para evitar la acumulación de código espagueti y deuda técnica de toggles, una vez que una Feature Flag se habilite al 100% en producción de forma estable durante más de 14 días, el agente tiene la obligación estricta de proponer una tarea inmediata de refactorización para remover la flag, eliminar las ramificaciones lógicas muertas del código y limpiar configuraciones asociadas.
- **Construcciones Herméticas (Network Hermeticity):** Los pipelines de CI/CD para la compilación de binarios y ejecución de pruebas deben configurarse bajo el principio de hermeticidad de red. Toda suite de compilación o testeo debe correr con el acceso a internet deshabilitado por completo, utilizando dependencias pre-cacheadas o proxies locales/registros internos inmutables (como Artifactory o Nexus) para prevenir inestabilidad y mitigar ataques de cadena de suministro.
- **Monorrepos Incrementales basados en Grafos:** En entornos de monorrepos extensos, es obligatorio utilizar herramientas de construcción basadas en grafos de dependencias (tales como Bazel, Nx o Turborepo). El pipeline de CI debe calcular el impacto del cambio mediante el grafo y ejecutar la compilación y las suites de pruebas exclusivamente sobre los módulos directamente o indirectamente impactados por el diff de Git, optimizando costos y tiempos de ejecución.

## 10. GESTIÓN AUTOMATIZADA Y CAMBIO INCREMENTAL DE BASE DE DATOS (EVOLUTIONARY DB DESIGN)
Migraciones Versionadas e Incrementales: El esquema de base de datos nunca debe modificarse manualmente. Todo cambio (esquema, vistas, índices, functions) debe entregarse mediante scripts de migración incrementales, inmutables y versionados (ej. `V1.1__add_status_column.sql`).
**Estrategia Obligatoria de Rollback (Reversión):** Toda migración incremental entregada en el proyecto debe contar de forma obligatoria con su correspondiente script de reversión o marcha atrás (ej. scripts `U1.1__...sql` en Flyway, o la directiva de rollback en Liquibase). Estos scripts de reversión deben ser validados en los entornos efímeros de prueba para garantizar un retorno seguro y sin pérdida accidental de datos.
**Límites de Bloqueo en Migraciones DDL (DDL Lock Timeouts):** Todo script de migración DDL que realice alteraciones estructurales (ej. `ALTER TABLE`, `CREATE INDEX` en modo bloqueante) debe definir de forma obligatoria un límite estricto de tiempo de bloqueo (ej. ejecutando `SET lock_timeout = '2s'` al inicio del script en PostgreSQL). Si la migración no puede adquirir el bloqueo exclusivo en 2 segundos, debe abortar y fallar para evitar encolar peticiones y saturar la base de datos en producción.
Patrón Expand-and-Contract (Zero-Downtime): Para cambios estructurales que afecten producción (ej. renombrar columnas o cambiar tipos), está prohibido realizar bloqueos destructivos. Se debe aplicar el patrón *Expand and Contract*:
  1. *Expand:* Agregar la nueva columna/tabla.
  2. *Migrate:* Sincronizar los datos temporalmente.
  3. *Contract:* Eliminar la columna/tabla antigua solo cuando la API ya no dependa de ella.
Ejecución Automática e Idempotente: El contenedor de la app o un job migrador (ej. Flyway/Liquibase) debe aplicar las migraciones pendientes automáticamente al arranque. Debe existir una tabla de historial (`flyway_schema_history` o equivalente) para garantizar idempotencia.
Separación de Responsabilidades: Los scripts de estructura (DDL), corrección de datos (DML) y datos semilla (Seeds) deben estar rigurosamente separados. Los Seeds nunca se ejecutan implícitamente en producción.
**Idempotencia Obligatoria en Seeds:** Todos los scripts de datos semilla (Seeds) deben diseñarse para ser estrictamente idempotentes. Deben usar sentencias condicionales (ej. `INSERT INTO ... ON CONFLICT DO UPDATE`, `INSERT IGNORE` o condicionales existenciales) de manera que puedan ejecutarse de forma repetida sin generar errores de clave duplicada, corrupción de datos o registros huérfanos.
- **Migración Asíncrona de Datos (DML Backfill Jobs):** Queda estrictamente prohibido ejecutar scripts DML masivos (que afecten a cientos de miles o millones de registros) de forma directa o sincrónica en las migraciones asociadas al despliegue. Toda mutación o migración de datos masiva debe ejecutarse mediante trabajos por lotes asíncronos (Backfill Jobs) fragmentados en lotes pequeños e incrementales (ej. 1,000 registros con pausas controladas de 500ms entre transacciones), garantizando disponibilidad del servicio y evitando bloqueos prolongados de tablas, agotamiento del pool e incidentes de falta de memoria (OOM).
Soporte Outbox: Los esquemas iniciales deben incluir obligatoriamente las tablas necesarias para el patrón Transactional Outbox.
**Gestión de Conexiones a Base de Datos (Pooling & Proxies):** Toda aplicación o microservicio debe configurar obligatoriamente un Pool de Conexiones limitado y optimizado en su adaptador de persistencia (ej. HikariCP, Knex/Prisma pooling config), definiendo máximos y mínimos de conexiones y timeouts de inactividad. En entornos serverless o de alta concurrencia, es obligatorio canalizar las conexiones a través de un proxy de base de datos (ej. PgBouncer, AWS RDS Proxy) para prevenir el agotamiento de sockets de red y memoria en el motor.

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
- **Control de Dependencias y Anclaje de Versiones (Version Pinning):** Está estrictamente prohibido instalar dependencias o paquetes externos sin autorización expresa. Todas las dependencias declaradas en el proyecto deben usar versiones fijas y exactas (ej. sin usar prefijos `^` o `~` en Node.js, versiones inmutables y exactas en Maven o Python). Es de cumplimiento obligatorio generar y comprometer en el repositorio los archivos de bloqueo correspondientes (`package-lock.json`, `pnpm-lock.yaml`, `poetry.lock`, etc.) para asegurar builds deterministas y mitigar ataques en la cadena de suministro.
- **Protección PII:** La información personalmente identificable debe ser enmascarada en los logs del servidor.
- **Validación Zero-Trust:** Todo input o DTO recibido de los Inbound Adapters debe validarse estrictamente contra un esquema definido (ej. Zod, Jakarta Validation) antes de delegarlo a la capa de Aplicación.

## 17. SRE Y RESILIENCIA (SITE RELIABILITY ENGINEERING)
- **Resiliencia y Tolerancia a Fallos (Circuit Breakers & Bulkheads):** Toda llamada HTTP saliente o integración con servicios de terceros y bases de datos debe implementar obligatoriamente límites de tiempo (Timeouts), reintentos con retraso exponencial (Exponential Backoff) y el patrón **Circuit Breaker** (Disyuntor) para evitar caídas en cascada. Asimismo, se deben configurar límites de concurrencia aislados (**Bulkheads**) por servicio integrado para evitar el agotamiento de sockets de red y pools de hilos de la aplicación ante caídas de componentes secundarios, proporcionando siempre respuestas de degradación elegante (fallbacks) de forma local.
- **Manejo Global de Excepciones (Global Exception Handling):** Queda estrictamente prohibido permitir que excepciones no capturadas provoquen la caída del hilo principal del microservicio (`unhandled exceptions` o crashes del proceso). Se debe implementar un controlador de excepciones global (ej. middlewares de Express/NestJS o `@ControllerAdvice` de Spring Boot) que intercepte cualquier error, asocie el `Correlation ID` correspondiente a la traza de log y devuelva una respuesta de error HTTP controlada y sanitizada (bajo el estándar RFC 7807) para mantener el microservicio en línea de forma ininterrumpida.
- **Ciclo de Vida y Apagado Elegante (Graceful Shutdown):** Todos los servicios del backend deben registrar manejadores de señales del sistema operativo (`SIGTERM` y `SIGINT`) para ejecutar un apagado elegante. Ante una señal de detención, el microservicio debe: 1) Detener la aceptación de nuevas peticiones entrantes. 2) Completar las transacciones y peticiones en vuelo durante un periodo de gracia parametrizado (ej. 15 a 30 segundos). 3) Liberar y cerrar ordenadamente las conexiones de base de datos y sockets antes de salir del proceso con un código de estado limpio.
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
- **Interfaces RPC Tipadas de Extremo a Extremo (Type-Safe API Boundaries):** Para evitar fallos silenciosos por discrepancias de payloads en producción, queda prohibido consumir APIs internas o microservicios a través de clientes HTTP dinámicos sin contratos. Se exige el uso de protocolos de comunicación con tipado estricto de extremo a extremo (tales como gRPC, tRPC o esquemas OpenAPI/Swagger autogenerados en compilación). Los tipos de datos del backend deben exportarse directamente como tipos estáticos en el frontend o servicio consumidor, bloqueando la compilación ante cualquier desajuste de firma.
- **Caché Distribuido:** Para aliviar la base de datos principal bajo alto tráfico, se exige la implementación de estrategias de Caché (ej. Redis/Memcached) usando patrones *Cache-Aside* o *Write-Through* con políticas estrictas de expiración (TTL).
- **DevX y Shift-Left Testing:** El desarrollo local debe utilizar entornos descartables idénticos a producción (DevContainers o Docker Compose). Se exige el uso de Pre-Commit Hooks (ej. Husky) para validar formato, linter, y seguridad estática *antes* de cada inyección de código.
- **Entornos Locales Deterministas con Nix (Nix-Based DevX):** Todo proyecto debe definir y proveer un entorno de desarrollo local reproducible mediante Nix (usando devenv, nix-shell o flakes). Esto garantiza que compiladores, bibliotecas dinámicas del sistema, herramientas del CLI de la nube y dependencias de la máquina local del desarrollador estén ancladas a nivel de bit y sean 100% consistentes con el pipeline de CI/CD, eliminando discrepancias operativas entre sistemas operativos.

## 21. CLEAN CODE EXTREMO Y CALIDAD ESTRUCTURAL
- **Complejidad Ciclomática Restringida:** Queda estrictamente prohibido escribir funciones o métodos con más de 10 ramificaciones lógicas (`if/else/switch`) o que superen las 50 líneas de código. Refactoriza obligatoriamente aplicando principios SOLID.
- **Tolerancia Cero a la Deuda Técnica (No Silenciadores):** Está prohibido generar código que contenga comentarios como `// TODO` o `// FIXME`. Adicionalmente, el Agente tiene **PROHIBIDO** silenciar advertencias del compilador o linter usando directivas como `// eslint-disable` o `@ts-ignore`. Todo warning debe solucionarse de raíz.
- **Limpieza de Código y Archivos en Desuso:** Al finalizar una tarea o refactorización, es obligatorio identificar y eliminar de forma inmediata cualquier archivo huérfano o residual (scripts de prueba locales, archivos `.old` o `.bak`, respaldos temporales), código comentado, funciones/variables sin referencias e imports no utilizados. El repositorio debe quedar 100% libre de residuos.
- **Prohibición de Números y Textos Mágicos:** Valores duros (ej. `if (status == 2)` o `type == "ADMIN"`) están baneados. Extraer obligatoriamente a Constantes o Enums documentados.
- **Anti-Objetos Dios (God Objects):** Queda prohibida la creación de clases monolíticas (ej. `UserService` con 3000 líneas). Obligatorio fragmentar responsabilidades usando Domain-Driven Design (Agregados y Casos de Uso específicos).

## 22. HARDCORE CODING Y DISEÑO DE SOFTWARE (NIVEL ARQUITECTO)
- **Inmutabilidad Estricta (Functional Core):** Queda prohibido mutar el estado de objetos en la capa de dominio. Todas las propiedades de las entidades y DTOs deben declararse como `readonly` (TS) o `final` (Java). Las modificaciones deben retornar clones del objeto (Zero-Side Effects).
- **Inyección de Dependencias Absoluta (Anti-`new`):** Está **ESTRICTAMENTE PROHIBIDO** usar la palabra clave `new` dentro de un Caso de Uso o Servicio para instanciar otra clase de lógica. Toda dependencia externa debe ser inyectada a través del constructor (Inversión de Control) para garantizar un código 100% testable.
- **Patrones de Diseño Obligatorios (Anti-If/Else):** Ante lógicas de negocio complejas, el agente debe reemplazar los bloques `if/else` condicionales por Patrones de Diseño Gang of Four (GoF). Ej: usar el *Patrón Strategy* para cálculos dinámicos, *Factory/Builder* para creación compleja, y *Observer* para flujos reactivos.
- **Zero-Allocation y Rendimiento Extremo:** Queda prohibido cargar colecciones masivas de bases de datos en la memoria RAM (Anti-OOM). El agente está obligado a procesar grandes volúmenes de datos usando *Iteradores, Streams, Pipes* o procesamiento por lotes (Batching/Paginación interna). Prohibidas las concatenaciones de strings pesadas dentro de bucles.

## 23. LA FRONTERA FINAL (OPERACIONES SINGULARITY)
- **Arquitectura "Viva" (C4 Model y ADRs Automáticos):** La IA tiene la obligación estricta de que el código sea su propia documentación. Por cada decisión estructural mayor, debe autogenerar un archivo *ADR (Architecture Decision Record)* y actualizar automáticamente un diagrama de **C4 Model** (usando sintaxis de Mermaid en Markdown) dentro del `CONTEXT.md`.
- **Ingeniería FinOps Autodirigida (Cost-Aware Code):** El agente está forzado a evaluar el costo financiero (en dólares de Cloud) de sus propios algoritmos e infraestructura. Tiene prohibido desplegar consultas de base de datos N+1 o flujos ineficientes si detecta que escalarán el costo del clúster innecesariamente. Obligación de desarrollar en modo *GreenOps* (menor consumo de CPU/RAM posible).
- **Business Observability (Telemetría de Negocio):** Queda prohibido limitarse a logs técnicos abstractos. El Agente debe instrumentar el código para emitir métricas financieras y operativas puras (ej. `"Transacción Fallida: -$500 USD perdidos"`) directamente hacia sistemas como Prometheus y Grafana, vinculando el código directamente con los KPIs del negocio.

## 24. EXTREME CLEAN CODE Y GESTIÓN DE REPOS
- **Formateo Inquebrantable (Husky & Pre-commit Hooks):** Queda estrictamente prohibido que el código sea empujado al repositorio sin haber sido formateado y lintado. El agente debe configurar obligatoriamente herramientas como **Husky**, **lint-staged**, **Prettier**, y **ESLint/SonarLint**. Cualquier advertencia sintáctica impedirá el commit automático.
- **Nomenclatura Idiomática (Específica por Tecnología):** Las convenciones sintácticas (ej. `camelCase`, `snake_case`, `PascalCase`, prefijos de interfaces) no serán globales, sino que **DEBEN adaptarse estrictamente a las guías de estilo oficiales del lenguaje y framework en uso** (ej. PEP 8 para Python, convenciones de Oracle para Java, estilo idiomático de Go o C#). 
- **Lenguaje Ubicuo Estricto (Domain-Driven Design):** A pesar de seguir la sintaxis de la tecnología, se prohíbe terminantemente el uso de sufijos técnicos genéricos como `Manager`, `Helper`, `Processor` o `Util`. Las clases, métodos y variables deben usar el lenguaje exacto del negocio (ej. `SettleInvoice` en lugar de `ProcessPayment`, o `OnboardEmployee` en lugar de `CreateUser`). El código es un manual de negocio, no un rompecabezas técnico.
- **Complejidad Cognitiva (Cognitive Complexity < 15):** Más allá de la complejidad ciclomática, la IA tiene prohibido escribir funciones cuya "Complejidad Cognitiva" (métrica de legibilidad humana) sea mayor a 15. Bucles excesivamente anidados o estructuras condicionales enrevesadas deben ser obligatoriamente extraídas a funciones privadas puras y autoexplicativas.
- **Semantic Versioning y Release Bots:** La gestión de versiones y tags Git será 100% invisible para el equipo humano. La IA debe configurar herramientas como `release-please` o `semantic-release` en los pipelines (CI/CD) para analizar los *Conventional Commits*, deducir algorítmicamente el salto de versión (SemVer), crear los *Tags Git* de manera automática, y autogenerar el `CHANGELOG.md` en cada fusión a `main`.

## 25. OPTIMIZACIÓN EXTREMA Y EJECUCIÓN WEBASSEMBLY (VANGUARD OPS)
- **Optimización Estática del AST (Abstract Syntax Tree):** En algoritmos de alto rendimiento, procesamiento de datos intensivo o bucles de cálculo crítico, el agente está obligado a auditar el Árbol de Sintaxis Abstracta (AST) del código generado. Debe aplicar optimizaciones estructurales explícitas a nivel de código (tales como desenrollado de bucles redundantes, inlining de funciones de llamada frecuente y eliminación de asignaciones de variables intermedias no utilizadas) para maximizar la velocidad de ejecución y reducir el consumo de ciclos de CPU antes del compilado final.
- **Servidor WebAssembly (Wasm/Wasi):** Para microservicios sin estado que requieran latencias mínimas y arranques en frío instantáneos (ej. integraciones de pasarela, enrutadores lógicos o parsers de datos), se exige compilar la lógica de negocio a módulos WebAssembly nativos utilizando WASI (WebAssembly System Interface). El pipeline de despliegue debe empaquetar y ejecutar estas cargas sobre entornos compatibles con Wasm (como Wasmtime, Wasmer o Cloudflare Workers Wasm Runtime) para reducir la huella de memoria RAM de megabytes a kilobytes y lograr un aislamiento seguro en sandbox a nivel de sandbox de bytecode.

## 26. DISEÑO DE CERO DEFECTOS (ZERO DEFECTS ENGINE)
- **Linter de Arquitectura (Layer Dependency Linter):** Para garantizar de forma estricta la direccionalidad de dependencias de la Arquitectura Hexagonal y Limpia, es obligatorio implementar y configurar herramientas automáticas de validación de dependencias de capas (ej. ArchUnit para Java, dependency-cruiser para Node/TS). El CI/CD debe abortar y vetar cualquier build si se detecta que módulos de dominio o aplicación importan componentes de la capa de infraestructura de forma directa o indirecta.
- **Tipado Estricto de Configuración (Config Schema Validation):** Queda prohibido el arranque o inicialización de cualquier servicio o microservicio si se detectan variables de entorno faltantes o con tipos de datos inválidos. Es de carácter obligatorio procesar y validar todas las configuraciones del sistema en el punto de arranque mediante parsers de esquema estricto (ej. Zod, Dry-types), arrojando excepciones descriptivas que impidan ejecuciones del servidor en estados corruptos.

## 27. CÓMPUTO HÍBRIDO CLÁSICO-CUÁNTICO (QUANTUM-CLASSICAL COMPUTE)
- **Desacoplamiento de Problemas Combinatorios Complejos:** Para problemas de optimización combinatorial complejos de alta densidad (tales como optimización de carteras financieras, planificación de recursos críticos o ruteo en tiempo real) que sean computacionalmente intratables en arquitecturas de CPU clásicas (problemas NP-hard), se prohíbe el uso exclusivo de heurísticas de fuerza bruta o aproximaciones secuenciales clásicas ineficientes.
- **Invocación a QPU:** Es obligatorio diseñar la lógica del backend desacoplada del cómputo combinatorial y estructurar adaptadores de salida compatibles con computación cuántica híbrida. El sistema delegará de forma asíncrona la resolución matemática de dichos problemas a unidades de procesamiento cuántico (QPUs) o recocedores cuánticos (Quantum Annealers) a través de APIs de nube y SDKs específicos (ej. Qiskit, Ocean SDK), procesando las muestras de resultados de forma clásica para retornar respuestas óptimas en tiempo logarítmico.

## 28. CONSENSO DE MODELOS MULTI-LLM EN PRODUCCIÓN (LLM CONSENSUS RUNTIME)
- **Consenso de Modelos en Caliente:** Para flujos y servicios de negocio críticos que dependan de la inferencia de modelos de lenguaje en tiempo de ejecución (ej. análisis de fraude, aprobación de transacciones o pre-diagnósticos de salud), se prohíbe acoplar la decisión a la respuesta de una única API o modelo de lenguaje local.
- **Protocolo de Votación y Fusión:** El backend debe estructurar adaptadores de salida que consulten en paralelo a un mínimo de 3 modelos heterogéneos independientes (ej. Gemini, Claude y GPT, o modelos locales en clústeres regionales), aplicando un algoritmo de votación semántica (basado en concordancia de tokens clave o similitud en embeddings) para determinar el resultado final. Si persiste un disenso irreconciliable de criterios entre los modelos, el flujo debe suspenderse de forma automática, desviar la operación a una compuerta de validación humana manual y emitir una alerta en los logs estructurados.