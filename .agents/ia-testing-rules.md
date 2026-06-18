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
   - Integración de **Playwright** (Web) o **Integration Tests** nativos (Flutter) para validar los *Happy Paths* críticos de usuario simulando un navegador/dispositivo real contra un ambiente de staging o contenedores efímeros.

### Objetivo General
Garantizar la estabilidad transaccional de un Monolito Modular de forma automatizada, permitiendo que las integraciones continuas rechacen cualquier PR (Pull Request) que rompa contratos, queries a base de datos, o flujos críticos de usuario.
