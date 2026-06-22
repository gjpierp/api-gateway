# Everything Agent System - Global Knowledge Base (Lecciones Aprendidas)

Este archivo contiene la base de conocimiento global acumulada por el enjambre de agentes de IA. Se utiliza para la auto-polinización y para inyectar "conciencia colectiva" en la Fase 0 de todo proyecto.

---

## 1. Concurrencia de Sesión (Anti-Spam de Pestañas)
* **Problema:** Los usuarios abren múltiples pestañas concurrentes desincronizando el estado de la aplicación MFE y saturando las conexiones de red.
* **Solución Técnica:**
  - Registrar y renovar cada sesión por pestaña usando un **Sorted Set (ZSET) en Redis** bajo la estructura de clave `safi:user:pages:{userId}`.
  - El valor del set es un UUID único de pestaña (`pageId`) y el score es el timestamp de expiración (Timestamp actual + margen de gracia de 35000ms).
  - El backend expone endpoints obligatorios: `POST /open-page` (purga scores expirados, verifica límite máximo de 5 pestañas e inserta), `POST /heartbeat` (renueva score de la pestaña activa), y `POST /close-page` (elimina la pestaña del set).
  - El frontend ejecuta un heartbeat cada 30 segundos y bloquea la interfaz de usuario con un Error Boundary de "Exceso de pestañas" si el backend rechaza el registro, limpiando el registro en el evento `beforeunload` de forma síncrona.

---

## 2. Privacidad Diferencial Analítica (OLAP)
* **Problema:** Ataques de re-identificación forense que deducen información de individuos específicos cruzando múltiples consultas analíticas agregadas consecutivas sobre datasets de PII.
* **Solución Técnica:**
  - Implementar ruido matemático controlado (siguiendo distribución Laplaciana o Gaussiana) en las consultas agregadas.
  - El presupuesto de privacidad global se define mediante parámetros acotados (\(\epsilon\), \(\delta\)).
  - Impedir consultas crudas sin ruido sobre bases de datos OLAP agregadas que contengan PII sensible.

---

## 3. Seguridad en Caliente (RASP con eBPF)
* **Problema:** Dependencias de terceros compromised que intentan realizar inyecciones de código o llamadas al sistema maliciosas en el contenedor de producción.
* **Solución Técnica:**
  - Utilizar programas de eBPF inyectados a nivel del kernel de Linux del host para filtrar syscalls procedentes de los contenedores.
  - Bloquear inmediatamente llamadas anómalas (como ejecución de binarios inesperados o conexiones externas no aprobadas en hilos específicos) y emitir alertas forenses inmutables.

---

## 4. Orquestación y Consenso del Enjambre (Swarm Orchestration)
* **Problema:** Sesgos cognitivos de monocultura de IA y colisiones lógicas al integrar código desarrollado en paralelo por subagentes.
* **Solución Técnica:**
  - **Consenso Heterogéneo:** Alternar modelos y proveedores de LLM según la criticidad (ej. Claude 3.5 para seguridad, Gemini para lógica central, modelos locales ligeros para diseño).
  - **Voto Ponderado con Veto Especialista:** En el debate de 5 críticos (`ia-reflection-rules.md`), asignar mayor peso o poder de veto al crítico especialista en base a la extensión de archivo modificada en el Git Diff.
  - **Gate de Integración Semántica:** Obligar a realizar una recompilación y pruebas de regresión cruzadas en una rama temporal efímera antes de fusionar los aportes concurrentes de múltiples subagentes.
  - **Filtro de Inyección Cognitiva (Shield Critic Gate):** Sanitizar y neutralizar instrucciones maliciosas en inputs de usuario o código externo de dependencias mediante un agente intermedio antes de inyectarlas en el contexto de los subagentes.
