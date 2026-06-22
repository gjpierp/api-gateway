[Español](README.md) | [English](README-en.md)

# Everything Agent System 🌌

Bienvenido al **Everything Agent System**, un framework de vanguardia para el desarrollo de software autónomo y multi-agente. Este repositorio contiene las reglas operativas, _gates_ (puertas de validación) y restricciones que gobiernan a un enjambre de agentes de IA especializados.

Este sistema está diseñado para simular una organización de TI Empresarial completa—desde Gestión de Producto y Diseño, hasta Arquitectura, Seguridad, FinOps, SRE y Cumplimiento Legal (Compliance).

## 🛠️ Uso

Para inicializar el enjambre en un proyecto:

1. Coloca la carpeta `.agents` en tu máquina local o en la raíz de tu repositorio.
2. Instruye a tu Asistente de IA: **"Inicializa el Everything Agent System leyendo `.agents/agents.md`"**.
3. La IA evaluará tu repositorio, detectará si es un proyecto nuevo o legacy, y esperará tu comando para iniciar la **FASE 0** o la **FASE 1**.

## 🚀 Características (Singularity Update)

- **Protocolo Agente Zero (Live-Sync):** El framework se inyecta globalmente. Cualquier proyecto nuevo se anclará por enlaces simbólicos (Junctions) a un único cerebro centralizado, garantizando sincronización en tiempo real de reglas.
- **Resiliencia Absoluta (0 Fallos):** Despliegues *Multi-Región Activo-Activo*, integración de *Chaos Engineering* (Chaos Mesh), y UIs indestructiblemente *Offline-First*.
- **Hardcore Coding y Calidad Extrema:** Los agentes están restringidos para operar con Complejidad Ciclomática controlada, *Inmutabilidad Estricta*, *Patrones de Diseño GoF* obligatorios y Tolerancia Cero a la Deuda Técnica (No TODOs, no silenciadores).
- **Stage-Gate Workflow:** Un enfoque estricto por fases que evita que las IAs escriban código antes de validar la arquitectura y la seguridad.
- **La Frontera Final:** Verificación formal matemática (TLA+/SMT), *Business Observability* directo a Grafana, ingeniería *FinOps* y generación automática de *Architecture as Code (C4 Model/ADRs)*.
- **Swarm Intelligence:** Múltiples agentes de IA debaten la calidad del código (Actor-Critic reflection) en lugar de una generación tradicional de un solo paso.
- **Self-Healing y Cron Agents:** Agentes de operaciones reproducen bugs autónomamente, despliegan remediaciones _zero-touch_, y un subagente nocturno escanea y auto-refactoriza librerías obsoletas en la madrugada.
- **Quantum-Safe & GRC:** Impone estricto cumplimiento legal, soberanía de datos y criptografía basada en celosías para seguridad a prueba del futuro.
- **Enrutamiento Semántico e Inferencia Edge:** Optimización de costos y latencia usando enrutadores semánticos livianos y ejecución WebGPU/WASM multihilo local en el navegador del cliente con consenso de modelos cruzados (Multi-LLM).
- **Score & OS Inmutables (Platform Engineering):** Declaración de recursos abstracta e independiente de la nube en `score.yaml` (Score) y despliegue sobre sistemas operativos minimalistas e inmutables (Talos/Flatcar Linux) con escalado FinOps reactivo (KEDA y Karpenter).
- **Seguridad en Runtime e Inmunidad (RASP con eBPF):** Defensa activa contra dependencias de terceros comprometidas a nivel de llamadas al sistema del kernel de Linux.
- **Privacidad Matemática Analítica (FHE & Privacidad Diferencial):** Inyección de ruido Laplaciano/Gaussiano en queries analíticos (OLAP) e indexación directa sobre campos encriptados sin descifrado en memoria (Fully Homomorphic Encryption).
- **Gobernanza Forense en Blockchain:** Historial de auditoría forense inmutable mediante firmas criptográficas y anclajes en un ledger distribuido (Blockchain) para repudio absoluto.
- **Pruebas de Alta Precisión y Resiliencia:** Pruebas de restauración automática de backups, detección de fugas de memoria, pruebas de regresión cruzada de motor (WebKit/Safari Matrix), simulación de enlaces satelitales degradados (Starlink) y caos en APIs de terceros.
- **Control de Concurrencia de Sesiones:** Restricción automatizada de pestañas/ventanas concurrentes por usuario mediante un set ordenado (ZSET) en Redis y ciclos de vida de heartbeat interactivos en el frontend.
- **Flexibilidad Arquitectónica y Tecnológica:** El enjambre de agentes y linters son 100% adaptables al lenguaje de programación utilizado y a la arquitectura declarada en el archivo `CONTEXT.md` o ADRs de la raíz del proyecto (PostgreSQL y Spring Boot como referencias).
- **Auto-Mutación Genética de Código:** Optimización continua nocturna de algoritmos mediante mutaciones sintácticas automáticas de IA, validadas con tests unitarios, de paridad y de carga (Fitness Functions).
- **Cómputo Híbrido Clásico-Cuántico:** Desacoplamiento y delegación asíncrona de problemas de alta complejidad combinatorial (NP-hard) a unidades de procesamiento cuántico (QPU) a través de APIs y SDKs especializados (ej. Qiskit).


## 🔄 El Ciclo de Vida de 6 Fases

El archivo `agents.md` actúa como la máquina de estados que gobierna todo el ciclo de vida:

- **FASE 0 (Ideación y Mutación UI):** Validación de producto, chequeos pragmáticos No-Code y diseño generativo de interfaces.
- **FASE 1 (Arquitectura y Edge):** Contextos Delimitados (Bounded Contexts), despliegues en el borde (Edge computing) y estrictos controles legales y FinOps.
- **FASE 2 (API-First):** Contratos OpenAPI, DTOs y casos de uso rigurosamente documentados.
- **FASE 3 (Core Dev y Reflexión):** Codificación Hexagonal/Legacy, TDD y un despiadado enjambre de debate de IAs para asegurar calidad matemática del código.
- **FASE 4 (DevSecOps):** Encriptación post-cuántica, auditoría SAST/SCA y generación de IaC (Terraform/Docker).
- **FASE 5 (SRE y Gemelos Digitales):** Pruebas de estrés pre-producción mediante tráfico sombra IA, y monitoreo Self-Healing en producción.

## 🤖 El Catálogo de Agentes
 
 El directorio contiene archivos de reglas especializados para cada agente autónomo:
 
 - `ia-product-rules.md` (Product & Agile)
 - `ia-design-rules.md` (UI/UX)
 - `ia-development-rules.md` (Ingeniería de Software)
 - `ia-code-review-rules.md` (Calidad de Código)
 - `ia-security-rules.md` (DevSecOps & Ciberseguridad)
 - `ia-devops-sre-rules.md` (DevOps, Infraestructura, SRE y FinOps)
 - `ia-memory-rules.md` (Contexto & ADRs)
 - `ia-handoff-rules.md` (Protocolos Inter-Agentes)
 - `ia-data-mlops-rules.md` (Ingeniería de Datos)
 - `ia-testing-rules.md` (TDD & Automatización)
 - `ia-ux-mutation-rules.md` (UIs Dinámicas)
 - `ia-edge-web3-rules.md` (Descentralización & Edge)
 - `ia-nocode-rules.md` (Pragmatismo / No-Code)
 - `ia-legacy-rules.md` (Reverse Engineering & Refactor)
 - `ia-compliance-rules.md` (GRC & Auditoría Forense)
 - `ia-reflection-rules.md` (Debate Actor-Crítico)
 - `ia-evolution-rules.md` (Fitness Functions)
 - `ia-digital-twin-rules.md` (Caos & Simulación)
 - `ia-subagents-rules.md` (Orquestación Paralela y Enjambre)

> **Nota:** El sistema respeta la autoridad humana. Aunque los agentes recomendarán fuertemente alternativas No-Code o patrones arquitectónicos específicos, el operador humano retiene el veto final y la autoridad de ejecución sobre las decisiones de arquitectura.
