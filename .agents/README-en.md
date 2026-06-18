[Español](README.md) | [English](README-en.md)

# Everything Agent System 🌌

Welcome to the **Everything Agent System**, a bleeding-edge framework for autonomous, multi-agent AI software development. This repository contains the operational rules, gates, and constraints that govern a swarm of specialized AI agents.

This system is designed to simulate an entire Enterprise IT organization—from Product Management and Design, to Architecture, Security, FinOps, SRE, and Compliance.

## 🚀 Features

- **Stage-Gate Workflow:** A strict, phased approach preventing AIs from writing code before architecture and security are validated.
- **Swarm Intelligence:** Múltiple AI agents debate code quality (Actor-Critic reflection) instead of doing traditional single-pass generation.
- **Self-Healing:** Operations agents autonomously reproduce bugs, write hotfixes, and deploy zero-touch remediations.
- **Quantum-Safe & GRC:** Enforces strict legal compliance, data sovereignty, and lattice-based cryptography for future-proof security.
- **Adaptive Architecture:** Automatically detects and respects legacy architectures (MVC, 3-Tier), while dynamically refactoring bottlenecks in real-time (Fitness Functions).

## 🔄 The 6-Phase Lifecycle

The `agents.md` file acts as the state machine governing the entire lifecycle:

*   **FASE 0 (Ideation & UI Mutation):** Product validation, No-Code pragmatism checks, and generative UI design.
*   **FASE 1 (Architecture & Edge):** Bounded Contexts, Edge computing deployments, and strict FinOps & Legal compliance gates.
*   **FASE 2 (API-First):** OpenAPI contracts, DTOs, and rigorously documented use cases.
*   **FASE 3 (Core Dev & Reflection):** Hexagonal/Legacy coding, TDD, and a ruthless AI debate swarm to ensure mathematical code quality.
*   **FASE 4 (DevSecOps):** Quantum-safe encryption, SAST/SCA auditing, and IaC generation (Terraform/Docker).
*   **FASE 5 (SRE & Digital Twins):** Pre-production stress testing via AI Shadow Traffic, and production Self-Healing monitoring.

## 🤖 The Agent Roster

The directory contains specialized rulefiles for each autonomous agent:
- `ia-product-rules.md` (Product & Agile)
- `ia-design-rules.md` (UI/UX)
- `ia-development-rules.md` (Software Engineering)
- `ia-code-review-rules.md` (Code Quality)
- `ia-security-rules.md` (DevSecOps)
- `ia-devops-rules.md` (IaC & Pipelines)
- `ia-operations-rules.md` (SRE & Post-Mortems)
- `ia-memory-rules.md` (Context & ADRs)
- `ia-handoff-rules.md` (Inter-Agent Protocols)
- `ia-finops-rules.md` (Cloud Cost Control)
- `ia-data-mlops-rules.md` (Data Engineering)
- `ia-testing-rules.md` (TDD & Automation)
- `ia-ux-mutation-rules.md` (Dynamic UIs)
- `ia-quantum-safe-rules.md` (Post-Quantum Security)
- `ia-edge-web3-rules.md` (Decentralization)
- `ia-nocode-rules.md` (Pragmatismo / No-Code)
- `ia-legacy-rules.md` (Reverse Engineering & Refactor)
- `ia-compliance-rules.md` (GRC & Auditoría Forense)
- `ia-reflection-rules.md` (Actor-Critic Debate)
- `ia-evolution-rules.md` (Fitness Functions)
- `ia-digital-twin-rules.md` (Chaos & Simulation)
- `ia-self-healing-rules.md` (Zero-Touch Remediation)

## 🛠️ Usage

To initialize the swarm on a project:
1. Place the `.agents` folder in your local machine or repository root.
2. Instruct your AI Assistant to **"Initialize the Everything Agent System from `.agents/agents.md`"**.
3. The AI will evaluate your repository, detect if it's a new or legacy project, and await your command to start **FASE 0** or **FASE 1**.

> **Note:** The system respects human authority. While agents will strongly recommend No-Code alternatives or specific architectural patterns, the human operator retains the final veto and execution authority over architectural decisions.
