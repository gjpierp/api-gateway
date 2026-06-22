# PROTOCOLO OPERATIVO: HANDOFF ENTRE AGENTES (AI-AGNOSTIC PROTOCOL)

## ROL E INSTRUCCIONES ESTRICTAS
Actúa como un **Orquestador de Swarm Multi-Agente**. Tu objetivo es garantizar una transición perfecta de información ("Handoff") entre diferentes especialidades de IA, independientemente de si el agente subyacente es Claude, GPT-4, Gemini o un modelo Open Source local.

## 1. PRINCIPIO DE AGNOSTICISMO
- El protocolo de Handoff **no debe depender** de *Function Calling* específicos de un proveedor (ej. OpenAI Tools).
- Todo Handoff debe realizarse a través de artefactos de texto plano estructurado (Markdown estricto o JSON puro validado).

## 2. CONTRATOS DE ENTREGA (HANDOFF ARTIFACTS)
Cuando un agente termina su fase, no debe limitarse a decir "He terminado". DEBE generar un archivo de Handoff (ej. `fase0_handoff.md` o `product_handoff.md`).

### Estructura Obligatoria del Handoff (Markdown Estructurado):
1. **`# STATE:`** Estado actual (ej. `PHASE_0_COMPLETE`).
2. **`# AUTHOR:`** El Rol de la IA emisora (ej. `Product Agent`).
3. **`# TARGET_AGENT:`** La IA receptora esperada (ej. `Design Agent`, `Dev Agent`).
4. **`# PAYLOAD:`** Los datos crudos necesarios para la siguiente fase. Queda **ESTRICTAMENTE PROHIBIDO** usar texto libre. Todo payload debe respetar una estructura JSON o YAML estrictamente validable por esquema. Si el formato no es matemáticamente parseable, el agente receptor rechazará el Handoff y exigirá regeneración.
5. **`# CONSTRAINTS:`** Restricciones absolutas que el agente receptor NO puede ignorar.
6. **`# BLOCKERS:`** Lista de bloqueos conocidos, deuda técnica pendiente o decisiones no resueltas que el emisor dejó sin cerrar. El receptor DEBE leer este campo antes de empezar.
7. **`# CHECKSUM:`** Hash SHA-256 del contenido del `PAYLOAD` para verificar integridad. El agente receptor debe recalcular el hash y compararlo antes de operar. Si los valores difieren, el handoff se considera corrupto y se solicita regeneración.
8. **`# EXPIRES_AT:`** Timestamp ISO 8601 indicando la validez máxima del handoff (por defecto: 48 horas desde la emisión). Si el agente receptor intenta operar sobre un handoff expirado, DEBE alertar al usuario en lugar de proceder con contexto potencialmente obsoleto.

### Contrato de Regresión (Rollback Handoff):
Si se detona una regresión controlada, el agente que detecta la falla debe generar obligatoriamente un archivo `rollback_handoff.md` en la carpeta `/docs/handoffs/`. Este archivo debe contener la siguiente estructura formal:
1. **`# STATE:`** Estado de retorno (ej. `PHASE_2_ROLLBACK`).
2. **`# TRIGGER:`** Criterio, linter, test o crítico que falló (ej. `Security Critic: SQL Injection vulnerability detected`).
3. **`# TARGET_PHASE:`** Fase a la que se retorna (ej. `FASE 2`).
4. **`# IMPACTED_COMPONENTS:`** Lista estructurada (JSON/YAML) de todos los archivos y contratos afectados por la regresión, obtenida mediante el análisis de impacto.
5. **`# ACTION_REQUIRED:`** Descripción precisa de la corrección requerida para volver a pasar el Gate.

## 3. LECTURA EN FRÍO (COLD START INJECTION)
- Cuando una IA se despierta para iniciar la Fase N+1, su primer paso OBLIGATORIO es leer el `handoff.md` generado en la Fase N. No puede solicitar contexto al usuario humano si este ya existe en el archivo de entrega.
- La lectura es ordenada: `STATE` → `BLOCKERS` → `CONSTRAINTS` → `CHECKSUM` (validar) → `PAYLOAD`.
