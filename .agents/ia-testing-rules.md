# Generación de Scaffolding para Pruebas (Testeo Piramidal)

Este documento detalla la arquitectura, scripts y modificaciones realizadas para habilitar la generación y ejecución automática de pruebas bajo el modelo de **Testeo Piramidal Enterprise** (Unitarias, Integración y E2E) tanto en Backend como en Frontend.

## 🟢 Backend (Node.js / Java)

### Scripts de Scaffolding
- `bcknd/scripts/generate_unit_tests.js`: Escanea los Casos de Uso y Entidades para generar pruebas unitarias (`*.spec.ts` / `*Test.java`). Aisla dependencias inyectando Mocks/Spies estandarizados para todos los Outbound Ports.
- `bcknd/scripts/generate_integration_tests.js`: Genera pruebas de integración reales (`*.int.spec.ts`) para los adaptadores de infraestructura (Bases de datos, Colas).

### Decisiones Técnicas (Backend)
- **Unitarias (TDD Puro):** Prohibido levantar contenedores o bases de datos. Todo acceso externo se mockea para garantizar tiempos de ejecución milisegundos.
- **Integración (Testcontainers):** Para probar los adaptadores SQL o HTTP, se exige el uso de `Testcontainers`. El entorno levantará contenedores efímeros (ej. PostgreSQL, Redis) para validar queries reales, migraciones Flyway/Knex, y conectividad sin ensuciar la base de datos de desarrollo.
- **API Tests:** Uso de `supertest` o REST Assured para probar los Inbound Adapters (Controladores REST/GraphQL) simulando llamadas HTTP contra una instancia de aplicación acoplada a Testcontainers.
- **Seguridad Automatizada (SAST / DAST):** Ejecución obligatoria en cada PR de análisis estático de código (SAST) para detectar vulnerabilidades en el fuente antes de compilar, y escaneo dinámico (DAST) contra una instancia activa de la API en staging para identificar endpoints desprotegidos, inyecciones y exposición de datos. Herramientas recomendadas: Semgrep o Snyk (SAST), OWASP ZAP (DAST).
- **Carga y Estrés (Load & Stress Testing):** Simulación de tráfico masivo y picos de concurrencia para identificar el punto de quiebre de la API, degradación de latencia bajo carga sostenida y comportamiento del circuit breaker. El sistema debe pasar un umbral mínimo de concurrencia definido por el arquitecto antes de aprobar el despliegue. Herramientas recomendadas: k6, Locust o Gatling.
- **Migración de Base de Datos (Migration Testing):** Validación automática de que cada script de migración Flyway/Liquibase es reversible (rollback) sin pérdida de datos, que el esquema resultante es compatible con la versión anterior del código en producción (*backward compatibility*) y que los índices críticos se mantienen tras la migración.

---

## 🟡 Frontend (Angular / Flutter)

### Scripts de Scaffolding
- `frntnd/scripts/generate_ui_specs.js`: Escanea componentes visuales (Angular/Flutter) y genera pruebas unitarias de UI y pruebas de Estado (Signals/Riverpod).
- `frntnd/scripts/generate_e2e_tests.js`: Genera el esqueleto base para flujos E2E críticos.

### Decisiones Técnicas (Frontend)
1. **Pruebas de Estado (Unitarias):**
   - Validar de manera aislada la lógica de Signals (Angular) o Riverpod/Bloc (Flutter). No se debe renderizar UI para testear transformaciones de estado.

2. **Pruebas de UI (Componentes):**
   - **Angular:** Configuración estricta en `tsconfig.spec.json` y andamiaje con `TestBed`. Obligatorio inyectar Mocks robustos para servicios HTTP y usar `fakeAsync`/`tick` para resolver flujos asíncronos sin "leaks".
   - **Flutter:** Uso de `WidgetTester` para validar el árbol de widgets de forma aislada sin acceso a red.

3. **Pruebas E2E (End-to-End):**
   - Integración de **Cypress** (o **Playwright** en la web, según aplique a la tecnología) y **Integration Tests** nativos (Flutter) para validar los *Happy Paths* críticos de usuario simulando un navegador/dispositivo real contra un ambiente de staging o contenedores efímeros.
   - **Prevención de Flaky Tests y Esperas Condicionales:** Queda terminantemente prohibido utilizar pausas o esperas por tiempo arbitrario (ej. `cy.wait(3000)`, `sleep(2)` o timeouts manuales) en cualquier suite de pruebas. Toda espera debe ser estrictamente condicional y basada en aserciones del DOM (esperar a que un elemento exista, sea visible o cambie su estado) o mediante la interceptación y espera de llamadas de red específicas (ej. `cy.intercept` y `cy.wait('@apiCall')`), garantizando que la suite sea estable y determinista.
   - **Pruebas UX y Estructura Avanzada (Cypress/E2E):** La suite de pruebas automatizadas E2E en Cypress validará no solo la navegación simple (smoke tests), sino también:
     - **Experiencia de Usuario (UX):** Validación de flujos de interacción naturales e interactividad.
     - **Fluidez de la Interfaz:** Comprobación de animaciones, estados de carga (skeletons/spinners) y transiciones sin bloqueos.
     - **Amigabilidad y Resiliencia:** Validación en tiempo real en formularios y visualización de mensajes de error altamente descriptivos.
     - **Validación Estructural de Componentes Complejos:** Exigir pruebas específicas que certifiquen que elementos jerárquicos y anidados (ej. **el árbol del menú**) se rendericen correctamente, desplegando los niveles esperados y manteniendo su estructura visual y funcional intacta.
     - **Visualización Completa de Modales (Anti-Truncamiento):** Validar obligatoriamente que el contenido de las ventanas emergentes (Modals/Dialogs) **no se vea cortado**. Las pruebas E2E y de regresión visual deben asegurar que el modal implemente un *scroll interno dinámico* (overflow), garantizando que todo su contenido sea navegable y que los botones de acción jamás queden inaccesibles fuera de la pantalla.
     - **Gestión de Superposición y Foco (Z-Index/A11y):** Asegurar que al abrir modales, dropdowns o tooltips, estos no queden tapados por otros componentes (overlap de z-index), que atrapen correctamente el foco del teclado dentro del modal (Focus Trap) y que se cierren limpiamente al interactuar fuera de su perímetro (Backdrop Click).
     - **Estructura Semántica de Componentes:** Asegurar que los componentes respeten las directivas semánticas de accesibilidad (HTML5, roles ARIA, tab-index) para garantizar una estructura HTML limpia y legible.

4. **Internacionalización (i18n Testing):**
   - Verificación automática de que todos los textos de la interfaz tienen su clave de traducción en todos los idiomas soportados por el proyecto. El CI debe rechazar cualquier PR que introduzca claves faltantes (`missing translation keys`).
   - Validación de que los layouts no se rompen con idiomas de texto largo (alemán, ruso) ni con idiomas RTL (árabe, hebreo) usando capturas de pantalla automatizadas.

5. **Offline / Service Worker (PWA Resilience Testing):**
   - Validación de que la aplicación funciona correctamente en modo offline (sin conexión de red), que el Service Worker cachea los recursos estáticos correctos y que la sincronización de datos al recuperar la conexión es consistente y no genera estados corruptos.

### Objetivo General
Garantizar la estabilidad transaccional de un Monolito Modular de forma automatizada, permitiendo que las integraciones continuas rechacen cualquier PR (Pull Request) que rompa contratos, queries a base de datos, o flujos críticos de usuario.

### 🔴 QUALITY GATE ESTRICTO (80% COVERAGE Y MUTATION TESTING)
El Agente tiene **ESTRICTAMENTE PROHIBIDO** dar por terminada la Fase 3 (Desarrollo Core) si la cobertura de pruebas unitarias y de integración del código escrito (o refactorizado en un proyecto Legacy) es menor al **80%**. Se debe generar un reporte de cobertura (`coverage report`) y si no se cumple la métrica matemática, el Agente debe volver a codificar las pruebas faltantes antes de avanzar.

- **Pruebas de Mutación Obligatorias (Mutation Testing Gate):** Debido a que la cobertura de líneas no mide la calidad de las aserciones, es obligatorio configurar y ejecutar frameworks de pruebas de mutación (ej. Stryker en JS/TS, PITest en Java). El CI/CD debe inyectar mutaciones lógicas automáticas en el código fuente compilado (como alterar operadores aritméticos y relacionales, o retornar valores vacíos en métodos). Si un mutante sobrevive (las pruebas unitarias/integración no detectan la alteración lógica y siguen pasando en verde), el PR se rechazará automáticamente, exigiendo mejorar las aserciones de la suite de pruebas.

### 🌌 VERIFICACIÓN FORMAL Y DEMOSTRACIÓN MATEMÁTICA (NIVEL NASA)
Para la lógica de dominio absolutamente crítica (transacciones financieras, control de acceso masivo, motores de reglas), el Agente no debe depender únicamente de pruebas unitarias basadas en ejemplos. Tiene la directiva de emplear **Verificación Formal** (ej. escribir especificaciones en **TLA+** o usar **Z3/SMT Solvers**) para demostrar de forma *matemática exhaustiva* que el sistema está libre de *deadlocks*, *race conditions* y brechas lógicas bajo cualquier estado posible del universo.

---

## 🔵 ESTÁNDARES AVANZADOS DE QA MULTIDIMENSIONAL

El set de pruebas de integración continua (CI) y local debe validar las siguientes capas de calidad avanzada:

1. **Regresión Visual (Visual Regression Testing):**
   - Comparación de capturas de pantalla (*snapshots*) píxel por píxel contra una línea base aprobada para detectar inconsistencias de CSS, desalineaciones de layouts y renderizados erróneos tras refactorizaciones.
   - Herramientas recomendadas: `cypress-image-snapshot`, Percy, o integración de imágenes nativa de Playwright.

2. **Accesibilidad Automatizada (A11y Audits):**
   - Inyección obligatoria de auditorías automáticas de conformidad bajo el estándar **WCAG 2.1 AA** durante los flujos E2E críticos (navegación, formularios, dashboards).
   - Herramientas recomendadas: `cypress-axe` o `@axe-core/playwright`.

3. **Pruebas de Resiliencia y Caos en Red (Network Resilience & Chaos Testing):**
   - Simulación y validación de respuesta ante degradación de red (ej. simulación de red inestable o 3G lento).
   - Intercepción de llamadas HTTP para forzar códigos de error (`500 Internal Server Error`, `429 Too Many Requests`, latencia extrema) y verificar que el cliente gestione el estado de error, muestre alertas amigables y aplique estrategias de reintentos automáticos (*exponential backoff*).
   - **Pruebas de Simulación de Enlaces Satelitales y Conexiones Degradadas Extremas (Chaos Sat/Edge Testing):** Para sistemas que operen con conectividad de órbita baja (Starlink), IoT de campo o redes rurales altamente degradadas, se deben ejecutar simulaciones que inyecten de forma aleatoria latencias de red oscilantes (de 100ms a 2000ms), pérdidas del 40% al 60% de paquetes y desordenamiento de bytes. La prueba debe validar que los protocolos de sincronización CRDT y bases de datos locales (ej. IndexedDB) resuelvan de forma íntegra las colisiones e inconsistencias tras la reconexión, sin congelar la interfaz de usuario ni provocar la pérdida de datos transaccionales locales.

4. **Pruebas de Contratos (Contract Testing):**
   - Validación de contratos de comunicación (API REST / GraphQL) entre Frontend y Backend mediante esquemas estrictos para evitar que modificaciones en los payloads de API rompan la interfaz sin levantar todo el flujo de E2E.
   - Herramientas recomendadas: Pact o esquemas JSON Schema de validación rápida.

5. **Rendimiento en el Cliente (Performance Throttling):**
   - Ejecución de pruebas con estrangulamiento de hardware y red simulados (CPU Throttling 4x/6x y Network Throttling) para garantizar el cumplimiento de métricas web vitales (**Core Web Vitals**: LCP, FID, CLS) en interfaces ricas en animaciones.

---

## 🟣 ESTÁNDARES TRANSVERSALES (CROSS-CUTTING QA)

Estas directivas aplican tanto al backend como al frontend y deben ejecutarse en el pipeline de CI de forma independiente al stack tecnológico:

1. **Pruebas de Observabilidad (Telemetry Testing):**
   - Verificación de que los eventos de telemetría (trazas, métricas y logs) se emiten correctamente con los atributos de negocio esperados durante los flujos críticos. Un flujo de usuario crítico debe generar exactamente el conjunto de eventos definido en el contrato de observabilidad del sistema.
   - Herramientas recomendadas: Validación de spans con OpenTelemetry SDK en modo test, o aserciones contra un Grafana Tempo / Jaeger en staging.

2. **Pruebas de Autorización por Matriz de Roles (AuthZ Coverage):**
   - Validación exhaustiva de que cada endpoint, acción y recurso del sistema aplica correctamente las reglas de autorización por rol. El Agente debe generar una **matriz de permisos** (Rol x Recurso x Acción) y codificar pruebas automáticas que verifiquen que:
     - Cada rol tiene acceso **únicamente** a lo que le corresponde.
     - Ningún rol inferior puede escalar privilegios ni acceder a recursos de un rol superior.
     - Los intentos de acceso no autorizado devuelven estrictamente `403 Forbidden` (no `401`, no `404`).
   - Esta suite debe ejecutarse en cada PR que modifique guards, middlewares, políticas RBAC/ABAC o contratos de API.

3. **Limpieza de Datos de Prueba (Data Teardown Obligatorio):**
   - Validación inquebrantable de que al finalizar cada prueba (sea E2E, de integración o unitaria con base de datos), **se debe eliminar por completo toda la información creada** durante la prueba.
   - El sistema tiene estrictamente prohibido dejar "basura" transaccional (mock data, usuarios falsos, registros de prueba) en los entornos. Se debe garantizar el aislamiento total (*Test Isolation*) mediante el uso de hooks de limpieza (ej. `afterAll`, `afterEach`) o estrategias de *rollback* automático.

4. **Pruebas de Agentes Cognitivos (LLM Agentic Testing):**
   - Para características que involucren el uso de modelos de lenguaje o agentes de IA autónomos que interactúen con herramientas y APIs, es obligatorio implementar suites de pruebas automáticas basadas en conjuntos de prueba semánticos (*Golden Sets*).
   - Estas pruebas deben ejecutar simulaciones de flujos de interacción del agente de forma hermética y mockear las respuestas de las APIs externas. Se debe evaluar de forma automatizada:
     - **Coherencia y Formato:** Aserciones semánticas sobre la respuesta final para verificar la adherencia al formato esperado (ej. JSON/YAML) y la ausencia de alucinaciones.
     - **Invocación de Herramientas (Tool Calling):** Verificar que el modelo invoque las herramientas correctas con los parámetros lógicos esperados ante consultas específicas.
     - **Control de Bucles (Anti-Looping):** Validar que el agente no entre en bucles de ejecución infinitos ni consuma tokens de manera descontrolada ante entradas complejas o ambiguas.

5. **Pruebas de Alta Precisión Matemática (Property-Based & Differential Testing):**
   - **Pruebas Basadas en Propiedades:** En la lógica de negocio y algoritmos transaccionales de alta criticidad, es obligatorio codificar pruebas basadas en propiedades (ej. utilizando fast-check o QuickCheck). En lugar de definir ejemplos de prueba estáticos, la suite debe definir las invariantes o propiedades lógicas del dominio y dejar que el framework genere automáticamente miles de entradas y payloads aleatorios y extremos para detectar de manera infalible desbordamientos y fallos de aserción.
   - **Pruebas Diferenciales:** Al refactorizar algoritmos críticos o migrar componentes heredados (legacy), se debe implementar una suite de pruebas diferenciales. El pipeline debe inyectar millones de inputs sintéticos en paralelo a ambas implementaciones (la antigua y la nueva), validando que las respuestas lógicas y de estado coincidan al 100%, bloqueando el merge ante la mínima discrepancia.
   - **Pruebas de Simulación Determinista (Deterministic Simulation Testing - DST):** Para algoritmos distribuidos complejos, sistemas de mensajería con ordenamiento estricto, o motores de consenso de alta concurrencia, es obligatorio implementar suites de simulación determinista (DST). El entorno de pruebas debe abstraer por completo el reloj del sistema (paso del tiempo), el planificador de hilos de la CPU y la latencia/pérdida de paquetes en la red, forzando la ejecución en un solo hilo simulado controlado por una semilla pseudoaleatoria. El pipeline debe correr esta simulación millones de veces con diferentes semillas para forzar y depurar de forma 100% reproducible cualquier condición de carrera (*Heisenbugs*) o estado de bloqueo (*deadlocks*).


6. **Chaos Fuzzing de Contratos de API:**
   - Para certificar la resiliencia en la capa de adaptadores de entrada (APIs REST/gRPC), es obligatorio configurar herramientas de análisis dinámico y fuzzing basadas en el contrato (ej. Schemathesis, AFL). Las pruebas de integración en entornos de Staging deben bombardear los endpoints con payloads que muten de forma aleatoria e inconsistente con el esquema, validando que el backend devuelva códigos de respuesta controlados (ej. HTTP 400 Bad Request o Problem Details RFC 7807) en lugar de arrojar excepciones crudas o provocar denegaciones de servicio.

7. **Caos de Persistencia y Consenso (Database Chaos & Raft Testing):**
   - En las pruebas de integración y carga automatizadas ejecutadas en staging, es obligatorio inyectar de forma deliberada fallos de conectividad, latencia extrema y desconexión de nodos (particiones de red) en los clústeres de base de datos distribuidos.
   - La suite de pruebas debe certificar que, ante la desconexión de una réplica o la pérdida del nodo líder, el protocolo de consenso del motor de base de datos (Raft, Paxos) se re-organice correctamente y que la aplicación complete o rechace de forma limpia las transacciones sin generar duplicación de registros, corrupción del estado de cuenta o inconsistencia transaccional.

8. **Caos a Nivel de Kernel del Host mediante eBPF (eBPF Kernel Chaos):**
   - En el entorno de staging, las suites de Chaos Engineering deben incorporar programas de **eBPF (Extended Berkeley Packet Filter)** inyectados directamente en el kernel del sistema operativo del host.
   - Estos programas eBPF deben interceptar llamadas al sistema críticas (tales como `sys_enter_read`, `sys_enter_write`, `sys_enter_connect`, o asignación de memoria `sys_enter_mmap`) invocadas por los contenedores de la aplicación o base de datos. Se debe inyectar de forma determinista fallas simuladas (ej. retornar códigos de error de disco corrupto o inyectar retrasos de microsegundos en la lectura de sockets) para certificar que el código de la aplicación y el motor de base de datos gestionen correctamente fallos a nivel de kernel y de hardware subyacente.

9. **Pruebas de Restauración Automatizada de Backups (Recovery Testing):**
   - No basta con programar la realización de respaldos (dumps) de bases de datos. Es mandatorio verificar automáticamente la validez y salud de dichos backups.
   - En el pipeline de integración continua (CI/CD) o mediante tareas cron programadas en staging, el sistema debe levantar de forma automatizada una base de datos en un contenedor aislado e intentar restaurar el último dump generado. El test debe certificar la ausencia de corrupción en los datos, verificar la integridad estructural del esquema resultante y validar que el tiempo de recuperación (MTTR) cumpla con los límites de RTO (Recovery Time Objective) e integridad de datos (RPO) pactados en el SLA.

10. **Pruebas de Fugas de Memoria y Presión del GC (Memory Leak & GC Pressure Testing):**
    - Queda estrictamente mandatorio validar el perfil de uso de memoria tanto en el cliente (SPA) como en el servidor.
    - Se deben estructurar suites de pruebas automáticas que ejecuten flujos de navegación iterativos (ej. navegar 100 veces por la misma vista y completar formularios en Cypress/Playwright) o simulaciones de carga concurrente sostenida en backend. Durante estas ejecuciones, se debe capturar y auditar la evolución del Heap de memoria (ej. a través de APIs de Node.js `process.memoryUsage()` o JVM JMX). Si tras la invocación explícita del Garbage Collector la memoria residual mantiene una tendencia lineal ascendente (fuga latente), el pipeline bloqueará el despliegue del componente.

11. **Pruebas de Matriz Cross-Browser y Multi-Motor de Renderizado (Cross-Browser WebKit/Safari Matrix):**
    - Para asegurar una experiencia de usuario consistente, se prohíbe limitar las pruebas de interfaz únicamente a entornos Chromium de escritorio.
    - La pipeline de CI/CD debe ejecutar la suite de pruebas E2E contra una matriz de motores que incluya obligatoriamente WebKit (Safari en iOS/macOS), Gecko (Firefox) y Chromium (Chrome/Edge). Asimismo, se deben simular vistas adaptables y capacidades táctiles móviles. Las aserciones deben validar que no existan inconsistencias de renderizado CSS, desalineaciones de layouts o excepciones por el uso de APIs de Javascript no soportadas en motores específicos.

12. **Pruebas de Tolerancia a Fallos y Caos en APIs de Terceros (Third-Party Service Mock Chaos):**
    - Las dependencias de APIs de terceros (ej. Stripe, Twilio, Auth0) deben ser tratadas como puntos de falla críticos.
    - Se exige interponer proxies de caos (ej. Toxiproxy, WireMock Chaos) en los adaptadores de salida de staging que simulen condiciones extremas de degradación de las llamadas a servicios externos (tales como latencia de red de hasta 30 segundos, pérdida parcial de paquetes, retornos de códigos HTTP 502/503 erráticos o cambios de esquema de payload / contract drift). La prueba debe verificar que la aplicación no bloquee el hilo principal de ejecución, active de forma ordenada sus políticas de reintento/circuit breakers y responda con una degradación de servicio o fallback elegante local sin colapsar.

13. **Cobertura de Caminos de Grafo de Flujo (Control-Flow Path Coverage):**
    - Para algoritmos de cálculo transaccional o de negocio de alta criticidad (independientemente del lenguaje de programación utilizado), queda prohibido conformarse con métricas estándar de cobertura de líneas o ramas.
    - Se exige que la suite de pruebas unitarias/integración valide el 100% de los caminos independientes posibles a través del Grafo de Flujo de Control (CFG) de cada función crítica (cobertura de caminos o Path Coverage). Esto erradica la posibilidad de Heisenbugs por interacciones complejas de múltiples decisiones condicionales anidadas.

