---
description: Genera automáticamente el andamiaje de pruebas de integración para Frontend (Angular/Vitest) y Backend (Node.js/Jest), detectando dinámicamente la estructura de directorios y aplicando reglas de comentarios en español.
---

Rol: Actúa como un Ingeniero de QA y Testing Automatizado.

Tarea: Generar el andamiaje de pruebas de integración para el backend (usando Jest/Supertest) y el frontend (usando Angular/Vitest) en el espacio de trabajo actual, adaptándote a la estructura de carpetas existente.

Fase 1: Descubrimiento del Entorno

Analiza el árbol de directorios y los archivos package.json para identificar dinámicamente cuál es la raíz del proyecto Backend (busca dependencias de Node, Express/Nest, y Jest) y cuál es la raíz del proyecto Frontend (busca dependencias de Angular y Vitest).

Si el proyecto sigue una Arquitectura Hexagonal o Clean Code, mapea las carpetas correspondientes a Controladores/Adaptadores de entrada, Modelos/Entidades de Dominio, y Rutas/Puertos.

Fase 2: Generación de Pruebas (Andamiaje)

Para el Backend detectado: Escanea los directorios que contengan la lógica de controladores, modelos y rutas de API. Por cada archivo sin su correspondiente prueba, genera un archivo .test.js (o .test.ts) utilizando la plantilla base de Jest. Si es un controlador o ruta, incluye la configuración de Supertest.

Para el Frontend detectado: Escanea los directorios en busca de componentes visuales (\*.component.ts). Por cada uno, genera un archivo .spec.ts configurado para Vitest, enfocado en probar la integración visual y el renderizado del DOM.

Fase 3: Cumplimiento y Validación

Regla Estricta de Idioma: Revisa el archivo Comentarios.txt en la raíz (si existe). Es obligatorio que todos los comentarios explicativos dentro de los archivos de prueba autogenerados estén redactados exclusivamente en español.

Ejecuta los scripts de test (npm run test o equivalente) en las respectivas carpetas detectadas utilizando la terminal integrada para validar que el andamiaje compila sin errores de sintaxis.

Entregable: Presenta un resumen estructurado indicando las rutas detectadas para Backend/Frontend, la cantidad de archivos creados en cada capa, y el resultado de la ejecución inicial de las pruebas.

Por qué esta versión es superior
Inferencia de Contexto: El Agente de Antigravity es lo suficientemente capaz como para leer un package.json o un angular.json y deducir dónde vive el código.

Adaptable a la Arquitectura: Se alinea con tu interés en la Arquitectura Hexagonal, buscando los patrones correctos (adaptadores, puertos, entidades) en lugar de nombres de carpetas genéricos.

A prueba de Monorepos: Funciona igual de bien si tienes todo en un monorepo (ej. Nx o Lerna) o en repositorios separados.
