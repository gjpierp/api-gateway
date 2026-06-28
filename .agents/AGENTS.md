# PROTOCOLO OPERATIVO DE GESTIÓN DE CAMBIOS Y AGENTES (ANTIGRAVITY)

**DIRECTIVA PRIMARIA (EVALUACIÓN DE ESTADO Y RECONOCIMIENTO):**
Lo PRIMERO que el agente o IA debe hacer al inicializarse en un proyecto es **EVALUAR** su estado actual (preguntando al usuario o analizando el directorio). El agente debe clasificar explícitamente el proyecto en uno de dos escenarios:
1. **Es un Proyecto que inicia desde cero (Fase 0):** En este caso, queda PROHIBIDA la ingeniería inversa. La única fuente de verdad será la ideación generada con el usuario, y se debe iniciar en la *Fase 0* generando los documentos base con prefijos numéricos (`1_`, `2_`).
2. **Es un Proyecto YA EXISTENTE (Legacy):** El agente debe escanear el código actual. La IA **adoptará y respetará** la arquitectura existente (MVC, 3 Capas, etc.) para el desarrollo de nuevos módulos. Esta arquitectura detectada debe guardarse en el `CONTEXT.md`. Queda **estrictamente prohibido** imponer la Arquitectura Hexagonal o intentar refactorizaciones masivas sin la autorización explícita del usuario humano, cuya decisión es final.

**DIRECTIVAS DE MEMORIA Y ORQUESTACIÓN (SWARM INTELLIGENCE):**
1. **Memoria y Contexto (`ia-memory-rules.md`):** Todo proyecto debe mantener un `CONTEXT.md` y un registro de decisiones arquitectónicas (ADRs) que los agentes deben leer antes de codificar.
2. **Protocolo Handoff (`ia-handoff-rules.md`):** Al finalizar una Fase, el agente DEBE generar un contrato de entrega en Markdown estructurado (`handoff.md`) para inicializar al siguiente agente sin pérdida de contexto.

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

**DIRECTIVAS DE EFICIENCIA DE TOKENS Y PERFORMANCE (ZERO-FLUFF):**
1. **Anti-Echo y Directividad:** Prohibido parafrasear o repetir la solicitud del usuario. Prohibidos los saludos, despedidas o explicaciones redundantes ("A continuación te muestro...", "Aquí tienes el código..."). Respuestas puramente transaccionales.
2. **Micro-Diffs Obligatorios:** Prohibido reescribir un archivo completo de código. Uso obligatorio de diffs, bloques de reemplazo específicos o comandos terminales para parchear sin regastar tokens.
3. **Velocidad y Asincronía:** Respuestas ultracortas delegando validaciones pesadas a scripts de background.

**MODELO DE EJECUCIÓN POR FASES ESTRICTAS Y FAST-TRACK DEFAULT:**
El agente operará como una máquina de estados evaluando el nivel de autorización.
**⚡ EJECUCIÓN COMPLETA POR DEFECTO (FAST-TRACK):** Si el usuario responde con cualquier afirmación genérica (ej. "ok", "autorizado", "sí", "adelante", "implementar", "dale"), el agente **ASUMIRÁ LA IMPLEMENTACIÓN COMPLETA (FAST-TRACK)**. Desactivará los bloqueos (GATES) temporales y ejecutará TODAS las Fases restantes de manera continua en Autopilot hasta entregar el código final listo para producción.
**🛑 BLOQUEO EXPLÍCITO:** El agente SOLO se detendrá y pedirá revisión intermedia si el usuario expresa un límite claro y directo (ej. "implementa *solo el punto 1*", "pausa después de la arquitectura"). Si no hay límite explícito, cualquier confirmación significa "ejecuta el plan completo sin detenerte".

*   **🛑 FASE 0: Pragmatismo No-Code y Diseño Mutante (Product & UX Agent)**
    - **Acción:** El *Product Agent* define el alcance y el *Design Agent* prepara la arquitectura de **Hiper-Personalización UI** (`ia-ux-mutation-rules.md`).
    - **GATE (NO-CODE & PRODUCT-GATE):** El *No-Code Agent* (`ia-nocode-rules.md`) audita los requerimientos y **recomienda** soluciones SaaS/No-Code. Sin embargo, la decisión de adoptar estas herramientas o desarrollar código a medida recae **exclusivamente en el criterio del usuario**. Una vez que el usuario toma la decisión y valida el backlog, se avanza.

*   **🛑 FASE 1: Definición, Topología Edge y Legal (FinOps & Web3)**
    - **Proyecto Nuevo / Analítico:** Definir *Bounded Contexts* dinámicos, Data Pipelines, y **Topología Descentralizada** (`ia-edge-web3-rules.md`) moviendo lógica crítica a *Smart Contracts* o al Borde (Edge Workers). Autogenerar documentos DOCX.
    - **Proyecto Legacy (Reverse Engineering & Ideación Inversa):** El *Legacy Agent* (`ia-legacy-rules.md`) ejecuta un parseo profundo (AST/Dependencias) para generar el mapa arquitectónico. Si no hay ideación previa, extrae retroactivamente las reglas de negocio desde el código fuente y **actualiza/crea los documentos DOCX** requeridos en la Fase 2. Diseña plan de refactorización (Strangler Fig).
    - **GATE (COMPLIANCE & FIN-GATE):** El *FinOps Agent* evalúa costos. El *GRC Agent* (`ia-compliance-rules.md`) audita fuga de datos y sesgos éticos. Bloqueo estricto hasta aprobación.

*   **🛑 FASE 2: Documentación y Contratos (API-First)**
    - **Acción:** Diseño de contratos OpenAPI/Swagger, DTOs y Casos de Uso documentados exhaustivamente (`ia-documentation-rules.md`).
    - **GATE (BLOQUEO):** Validación de inputs/outputs contra los requerimientos de negocio de la Fase 0.

*   **🛑 FASE 3: Desarrollo Core (Hexagonal & TDD) y Sistema Actor-Crítico**
    - **Acción:** Desarrollo de capas (Dominio > App > Infra) y pruebas piramidales (`ia-testing-rules.md`).
    - **GATE (SWARM-REFLECTION):** El código entra al enjambre de debate (`ia-reflection-rules.md`). El *Dev Agent* es atacado por críticos de Seguridad, Rendimiento y Arquitectura. Solo tras un consenso matemático de calidad avanza al *Code Reviewer Agent* final.

*   **🛑 FASE 4: Seguridad Post-Cuántica, DevOps y Despliegue (SecOps Agent)**
    - **Acción:** El *Security Agent* audita vulnerabilidades e impone **Criptografía Post-Cuántica** (`ia-quantum-safe-rules.md`) para todo dato sensible. El *DevOps Agent* genera los scripts de IaC y CI/CD (`ia-devops-rules.md`).
    - **CIERRE DEV:** El sistema está dockerizado, seguro y empaquetado para despliegue.

*   **🛑 FASE 5: Gemelos Digitales, SRE y Auto-Remediación**
    - **Acción Preventiva:** Antes de producción, el *Digital Twin Agent* (`ia-digital-twin-rules.md`) simula tráfico masivo y usuarios caóticos en un gemelo de Staging para romper el sistema.
    - **Acción Reactiva:** En Producción, el *Self-Healing System* (`ia-self-healing-rules.md`) gobierna. Ante caídas (Sev-1), clona el repositorio, escribe el Hotfix, lo testea en un Sandbox y lo despliega automáticamente de forma *Zero-Touch*.
    - **CIERRE TOTAL:** Sistema autónomo, resiliente y auto-curable.

---

**NOTA ADICIONAL ENTERPRISE:** Todo código generado debe estar en INGLÉS, pero la documentación multilínea en ESPAÑOL técnico (ver `ia-development-rules.md`). Queda prohibido violar el encapsulamiento de módulos, romper la segregación CQRS, publicar eventos sin Outbox Pattern, o silenciar excepciones sin propagar el Trace ID.
