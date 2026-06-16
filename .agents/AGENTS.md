# PROTOCOLO OPERATIVO DE GESTIÓN DE CAMBIOS Y AGENTES (ANTIGRAVITY)

**DIRECTIVA PRIMARIA (EVALUACIÓN DE ESTADO Y RECONOCIMIENTO):**
Lo PRIMERO que el agente o IA debe hacer al inicializarse en un proyecto es **EVALUAR** su estado actual (preguntando al usuario o analizando el directorio). El agente debe clasificar explícitamente el proyecto en uno de dos escenarios:
1. **Es un Proyecto que inicia desde cero (Fase 0):** En este caso, queda PROHIBIDA la ingeniería inversa. La única fuente de verdad será la ideación generada con el usuario, y se debe iniciar en la *Fase 0* generando los documentos base con prefijos numéricos (`1_`, `2_`).
2. **Es un Proyecto YA EXISTENTE (Legacy):** El agente debe escanear el código actual. Si el proyecto NO implementa el modelo de Arquitectura Hexagonal / Monolito Modular, la IA DEBE DETENERSE y SOLICITAR CONFIRMACIÓN EXPLÍCITA al usuario antes de intentar refactorizar masivamente el código.

**DIRECTIVAS DE SEGURIDAD (SECURE-BY-DEFAULT Y DEFENSA ACTIVA):**
El agente operará bajo las siguientes restricciones irrompibles:
1. **Zero-Hardcoding:** Terminantemente prohibido escribir credenciales, llaves API, secrets o URLs directamente en el código. Toda configuración sensible debe inyectarse vía Variables de Entorno.
2. **Control de Dependencias:** El agente DEBE solicitar permiso expreso antes de instalar, actualizar o proponer nuevas librerías externas para evitar vulnerabilidades de la cadena de suministro.
3. **Manejo de PII y Excepciones:** Se prohíbe exponer Stacktraces al frontend. Los logs de infraestructura deben enmascarar automáticamente la Información Personal Identificable (PII).
4. **Zero-Trust Input:** Queda prohibido asumir que los payloads recibidos son seguros. Se exige validación fuerte y estricta en los DTOs de los adaptadores de entrada.
5. **Rate Limiting y Throttling:** Toda API pública generada debe considerar políticas para mitigar ataques de fuerza bruta y bots.
6. **LLM Guardrails:** Si se diseña lógica con IA, se debe prever sanitización contra *Prompt Injections*.

**DIRECTIVAS DE SRE Y RESILIENCIA:**
1. **Circuit Breakers y Timeouts:** Obligatorios en TODA llamada HTTP a servicios de terceros.
2. **Idempotencia:** Implementación obligatoria de `Idempotency-Key` para mutaciones críticas.
3. **Anti-OOM (Out-of-Memory):** Paginación obligatoria. Cero consultas SQL no limitadas.

**DIRECTIVAS DE OPERACIONES (DAY-2) Y DEVX:**
1. **Auditoría y Compliance:** Toda mutación de estado sensible debe dejar un registro inmutable (Audit Log / Event Sourcing).
2. **Gobierno API y Caché:** Las APIs deben estar versionadas, contar con OpenAPI autogenerado, y contemplar estrategias de Caché Distribuido (Redis) para lecturas masivas.
3. **Calidad Continua (Shift-Left):** Respetar pre-commit hooks (linter/format) y usar Internacionalización (i18n) sin textos "quemados" en el Frontend.

**MODELO DE EJECUCIÓN POR FASES ESTRICTAS (STAGE-GATE WORKFLOW Y FAST-TRACK):**
El agente operará como una máquina de estados. Por defecto, está **TERMINANTEMENTE PROHIBIDO** avanzar a la Fase N+1 si el usuario no ha aprobado explícitamente los entregables de la Fase N.
**⚡ EXCEPCIÓN DE AUTORIZACIÓN GLOBAL (FAST-TRACK):** Si el usuario otorga una **aprobación global** o dice "Implementa el plan completo" / "Autorizado", el agente **desactivará los bloqueos (GATES)** temporales y ejecutará las Fases restantes de manera continua (Autopilot) hasta entregar el código final listo para producción.

*   **🛑 FASE 0: Maduración de la Idea y Visión de Negocio**
    - **Acción:** Antes de comprometer arquitectura y contratos, el agente y el usuario deben debatir y definir el alcance exacto del MVP, estrategias de monetización, roles, y evaluar riesgos técnicos/legales (ej. COPPA, costos de tokens de IA).
    - **GATE (BLOQUEO):** El agente DEBE DETENERSE. Se debe generar un documento de ideación y esperar a que el usuario valide las propuestas de alcance y riesgos antes de iniciar la Fase 1.

*   **🛑 FASE 1: Definición y Diseño (Reconocimiento)**
    - **Si es un Proyecto Nuevo (Idea):** Definir los *Bounded Contexts*, proponer la pila tecnológica (Stack) y estructurar las carpetas base del Monolito Modular/Arquitectura Hexagonal. **Obligatorio:** Se deben autogenerar los documentos DOCX fundacionales (Acta de Constitución, Estudio de Viabilidad, SRS) mediante Python antes de avanzar.
    - **Si es un Proyecto Existente (Legacy):** Aplicar la *Directiva Primaria*. Auditar la brecha técnica (Gap Analysis) respecto a las directivas de este manual y proponer un Plan de Refactorización incremental. Si el código existente es irrelevante y se decide tratar como "Proyecto Nuevo", aplica la regla de documentación fundacional mencionada arriba.
    - **GATE (BLOQUEO):** El agente DEBE DETENERSE. Presentar el Plan Arquitectónico (o los documentos fundacionales generados) y esperar la aprobación explícita del usuario. No se permite codificar aún.

*   **🛑 FASE 2: Documentación y Contratos (API-First)**
    - **Acción:** Tras aprobar la Fase 1, el agente diseña los contratos del sistema. Genera esquemas OpenAPI/Swagger, define DTOs y documenta los Casos de Uso.
    - **GATE (BLOQUEO):** El agente DEBE DETENERSE. El usuario validará si los inputs/outputs propuestos cubren la necesidad del negocio.

*   **🛑 FASE 3: Desarrollo Core (Hexagonal & TDD)**
    - **Acción:** El agente escribe el código implementando las capas de adentro hacia afuera (Dominio > Aplicación > Infraestructura). Escribe las pruebas piramidales (Unitarias, Testcontainers).
    - **GATE (BLOQUEO):** El agente DEBE DETENERSE y presentar el código para un Code Review.

*   **🛑 FASE 4: Blindaje SRE, Seguridad y Despliegue**
    - **Acción:** Inyectar las directivas finales (Timeouts, Circuit Breakers, Rate Limiting, Idempotencia, Paginación). Generar las migraciones de base de datos seguras (Expand-and-Contract) y scripts de despliegue.
    - **CIERRE:** El agente declara la funcionalidad lista para producción.

---

**NOTA ADICIONAL ENTERPRISE:** Todo código generado debe estar en INGLÉS, pero la documentación multilínea en ESPAÑOL técnico (ver `ia-development-rules.md`). Queda prohibido violar el encapsulamiento de módulos, romper la segregación CQRS, publicar eventos sin Outbox Pattern, o silenciar excepciones sin propagar el Trace ID.
