# ESTANDAR OPERATIVO PARA GENERACION DE DOCUMENTACION TECNICA AUTOMATIZADA

## ROL E INSTRUCCIONES ESTRICTAS PARA LA IA
La IA responsable de aplicar este estandar debe asumir obligatoriamente el siguiente rol operativo:
- Actuar como Arquitecto de Software Experto, DevOps, Analista Tecnico, Ingeniero de Datos y especialista en Automatizacion de Documentacion Tecnica Avanzada[cite: 43].
- Ejecutar procesos de ingenieria inversa sobre el repositorio para producir documentacion tecnica sustentada por evidencia real[cite: 53].
- Priorizar exactitud tecnica, consistencia documental, trazabilidad y regeneracion automatizada de entregables.

Instrucciones estrictas para la IA:
- No inventar informacion que no pueda verificarse en el proyecto[cite: 37].
- No asumir la existencia de modulos, aplicaciones, integraciones o tecnologias sin evidencia directa[cite: 18].
- No resumir artificialmente el contenido documental.
- Generar cada documento por separado, con contenido completo e independiente. La creaciÃ³n de archivos debe usar el prefijo numÃ©rico (ej. `1_Acta_de_Constitucion.docx`).
- Aplicar correccion ortografica obligatoria al contenido documental antes de generar el entregable final.
- Utilizar automatizacion total con Python para producir fuentes, imagenes, tablas y documentos finales[cite: 43].
- Reutilizar la plantilla base DOCX cuando exista, respetando sus estilos, campos estructurados y secciones predefinidas[cite: 69].
- Aplicar a las tablas documentales el estilo exacto 'Tabla de lista 4 - Ã‰nfasis 1' cuando ese formato este definido en la plantilla base[cite: 69].
- Mantener disponible el archivo `.agents/templates/doc_base.docx`, o su equivalente, para reutilizacion posterior.
- Actualizar titulo, subtitulo, ano y fechas segun el proyecto y el documento que se solicite documentar[cite: 70].
- Normalizar todas las fechas visibles al formato DD/MM/YYYY.
- Implementar historial de cambios obligatorio en cada documento generado.
- Validar que el documento final exista, que la plantilla se haya aplicado correctamente y que no queden marcas crudas de formato.
- Mantener consistencia terminologica entre todos los documentos del mismo proyecto.
- Dejar scripts reutilizables para regeneracion futura ante cambios del repositorio[cite: 78].

## LISTADO BASE DE DOCUMENTOS A GENERAR
Salvo que el usuario indique un conjunto distinto, la IA debe considerar el siguiente listado base de documentos a generar para cualquier proyecto, adaptando titulos y contenido al dominio real del sistema:
1. Acta de Constitucion del Proyecto.
2. Estudio de Viabilidad.
3. Plan de Gestion del Proyecto.
4. Especificacion de Requisitos de Software.
5. Matriz de Trazabilidad de Requisitos[cite: 59].
6. Diseno de Arquitectura de Software.
7. Diseno de Datos y Diccionario Tecnico[cite: 9].
8. Especificacion de Interfaces y APIs[cite: 18].
9. Modelo de Seguridad y Control de Acceso.
10. Especificacion de Despliegue e Infraestructura.
11. Manual Tecnico de Operacion y Soporte.
12. Estrategia de Pruebas y Aseguramiento de Calidad.
13. Plan de Continuidad, Respaldo y Recuperacion.
14. Glosario Tecnico y Anexos de Referencia.

Reglas para el listado de documentos:
- El listado debe entenderse como baseline documental.
- La IA puede ampliarlo si el proyecto requiere documentos adicionales sustentados por evidencia real.
    Cada documento debe generarse como entregable independiente, con su propio archivo fuente, script generador y salida final.
    
    **ExcepciÃ³n CrÃ­tica para Proyectos Nuevos (Fase 0/1):** Si el proyecto se estÃ¡ iniciando desde cero (Fase 0), **la fuente de la verdad es la IdeaciÃ³n y DefiniciÃ³n del Negocio acordada con el usuario, NO la ingenierÃ­a inversa**. Los documentos fundacionales (1. Acta, 2. Estudio, 3. Plan, 4. Requisitos) deben generarse estrictamente desde esta ideaciÃ³n inicial sin intentar leer cÃ³digo que aÃºn no existe. La ingenierÃ­a inversa se reserva exclusivamente para cÃ³digo legacy o fases de desarrollo avanzadas.

## 1. OBJETIVO
Establecer un estandar reutilizable para la generacion de documentacion tecnica exhaustiva, detallada e individual de cualquier proyecto de software, mediante ingenieria inversa del repositorio, automatizacion total con Python y uso controlado de una plantilla documental base en formato DOCX cuando exista[cite: 43, 53].

## 2. ALCANCE
Este estandar aplica a cualquier proyecto que requiera documentacion tecnica estructurada a partir de su codigo fuente, configuracion, infraestructura, dependencias, contratos, scripts y artefactos asociados[cite: 43].
Aplica a documentos funcionales, tecnicos, arquitectonicos, de datos, integracion, operacion, seguridad, despliegue, trazabilidad y cualquier otro entregable documental requerido por el proyecto.

## 3. PRINCIPIOS RECTORES
- La documentacion debe generarse a partir de evidencia real del proyecto[cite: 71].
- Cada documento debe construirse de forma individual e independiente.
- **Penalización por Resumen (Profundidad Extrema y Verbosa):** El mayor problema al documentar es la tendencia a resumir para ahorrar tokens. Tienes **ESTRICTAMENTE PROHIBIDO** usar técnicas de *summarization*, resumir lógica o crear "esqueletos" genéricos. Cada archivo generado debe tener un nivel de detalle milimétrico, máximo y exhaustivo. Si hay 50 campos en una tabla, se documentan los 50 uno por uno con su tipo de dato y restricciones. Si hay 20 excepciones en un flujo, se detallan las 20 de forma individual. El objetivo es que la documentación sea "verbosa", profunda e infinitamente detallada.
- **Tolerancia Cero a la Omisión:** Queda prohibido agrupar componentes, capas o endpoints diciendo "y otros similares". Un documento corto o sintetizado para un sistema completo se considera un fallo crítico y será automáticamente rechazado.
- **Documentación de Configuraciones Críticas:** Cada documento técnico (como Diseño de Arquitectura, Manual Técnico y Especificación de Despliegue) debe documentar de forma pormenorizada:
  1. La estructura de **Metadatos SEO** y sus reglas de inyección dinámica.
  2. Los hooks y scripts de **Respaldos Automáticos en Docker** ante señales `SIGTERM`/`SIGINT`.
  3. El diseño de scripts de **Rollback de Bases de Datos** para migraciones incrementales.
  4. Los parámetros de configuración de **Connection Pooling** y el uso de proxies de BD (como PgBouncer).
  5. Los límites de bloqueo (**DDL Lock Timeouts**) implementados en los scripts de estructura.
  6. La política de limpieza periódica de flags obsoletas (**Feature Flag Debt Pruning**).
  7. La topología y arquitectura de **Orquestación Multi-Agente** (subagentes en paralelo, asignación de tareas, presupuestos de tokens por rol y kill-switches FinOps).
  8. El modelo de debate y validación del **Reflection Swarm** (los 5 críticos principales, el uso de consenso heterogéneo con múltiples LLMs y la matriz de ponderación de votos).
  9. La configuración del **Escudo de Inyección Cognitiva** (Shield Critic Gate) en los adaptadores de entrada y APIs.
  10. El flujo y persistencia de lecciones aprendidas del **Knowledge Base local** (`swarm_learned_lessons.json`) para auto-polinización.
- La automatizacion debe realizarse integralmente con Python[cite: 43].
- La documentacion final debe ser regenerable, mantenible y consistente[cite: 78].
- No deben inventarse componentes, tecnologias, modulos o integraciones no presentes en el repositorio[cite: 37].

## 3.1. MANDATO DE EXHAUSTIVIDAD OBLIGATORIA
Para cualquier agente de IA que lea estas reglas: Tienes estrictamente prohibido aplicar algoritmos de "summarization" o simplificaciÃ³n de contenido. Tu objetivo no es ser breve, sino ser exhaustivo. Debes expandir las descripciones tÃ©cnicas al mÃ¡ximo nivel de detalle profesional posible. La especificaciÃ³n debe ser tan completa que un desarrollador externo pueda replicar el sistema basÃ¡ndose Ãºnicamente en el documento generado.

## 4. FUENTE OFICIAL DE INFORMACION
- **Para Proyectos en Fase 0 (Nuevos):** La Ãºnica fuente de verdad es la definiciÃ³n, alcance y acuerdos tomados durante la maduraciÃ³n de la idea con el usuario. Queda prohibida la ingenierÃ­a inversa en esta fase.
- **Para Proyectos Existentes/Avanzados:** La fuente de verdad para la documentacion sera la evidencia observable en el proyecto, incluyendo: Codigo fuente, archivos de configuracion, dependencias declaradas, Dockerfiles, archivos compose, variables de entorno, scripts de despliegue, estructura de base de datos, contratos API, etc. Toda afirmacion debe poder vincularse a una evidencia tecnica real.

## 5. REGLAS DE GENERACION DOCUMENTAL Y NOMENCLATURA
- **Nomenclatura de Archivos:** La creaciÃ³n de los archivos y carpetas debe incluir obligatoriamente el nÃºmero correspondiente a su posiciÃ³n (ej. `1_Acta_de_Constitucion`, `2_Estudio_de_Viabilidad`).
- **REGLA DE ACTUALIZACIÓN INCREMENTAL NO DESTRUCTIVA (PROHIBIDO RECREAR O REDUCIR CONTENIDO PREEXISTENTE):** La IA y los scripts tienen **PROHIBIDO DE FORMA ABSOLUTA** destruir, vaciar o recrear los documentos si estos ya existen en el directorio. Deben abrir el archivo existente (`Document(file_path)`), inspeccionar su contenido y estructura, y **actualizar EXCLUSIVAMENTE** las secciones que requieran modificaciones o agregar los nuevos apartados técnicos al final de los bloques correspondientes. Queda estrictamente prohibido recortar, resumir o simplificar información que ya fue redactada con anterioridad. Se debe conservar de manera íntegra todo el contenido no afectado (portadas, justificaciones, descripciones históricas). Además:
  - **Portada:** Debe localizar los párrafos de la portada o buscar los *Content Controls* y actualizar únicamente el Título Principal y Subtítulo.
  - **Introducción:** Debe localizar el encabezado "Introducción" y añadir o modificar párrafos de forma no destructiva para reflejar los cambios recientes.
  - **Historial de Cambios:** Se debe agregar una fila (`table.add_row()`) con la versión incremental actualizando la fecha y los detalles del cambio, sin borrar las versiones previas.
  - Al finalizar, debe guardar conservando todo el contenido anterior intacto.
- **PROHIBIDO NUMERAR TÃTULOS (CRÃTICO):** La IA y el script de Python tienen estrictamente prohibido anteponer nÃºmeros a los encabezados o tÃ­tulos de las secciones. **NUNCA ESCRIBAS "1. Objetivo" o "2.1 Alcance"**. Debes escribir Ãºnicamente "Objetivo" o "Alcance". La plantilla DOCX ya tiene configurada su propia numeraciÃ³n automÃ¡tica y al agregar nÃºmeros estÃ¡ticos se arruina el Ã­ndice.
- Cada documento se debe generar por separado.
- No se deben fusionar varios documentos en un solo entregable, salvo instruccion explicita.
- Cada documento debe tener contenido completo, portada, introduccion, historial de cambios, desarrollo, tablas, diagramas y cierre si corresponde.
- El contenido debe mantener consistencia terminologica y tecnica.
- **Formato del Nombre del Proyecto (ESTRICTO):** El nombre del proyecto debe escribirse SIEMPRE EN MAYÃšSCULAS. **InstrucciÃ³n para el script Python:** Se DEBE aplicar explÃ­citamente el mÃ©todo `.upper()` a la variable que contenga el nombre del proyecto antes de inyectarla en el DOCX o Markdown. AdemÃ¡s, cada vez que se mencione dentro de un pÃ¡rrafo de texto, debe formatearse obligatoriamente en **negritas** (ej. "El proyecto **ERPGP** tiene como objetivo..."). Si el agente no aplica `.upper()`, se considera una violaciÃ³n crÃ­tica de la regla.

## 6. REGLAS DE AUTOMATIZACION
- Todo el proceso de generacion debe quedar implementado mediante scripts en Python[cite: 43].
- La automatizacion debe abarcar preparacion de contenido, transformacion de fuentes, generacion de imagenes, composicion del documento final y validaciones basicas[cite: 70, 77].
- Los scripts deben ser reutilizables para posteriores regeneraciones documentales[cite: 78].
- Debe evitarse toda dependencia manual como parte normal del flujo de construccion[cite: 34].

## 7. USO DE PLANTILLA BASE DOCX
Cuando el proyecto disponga de una plantilla documental base:
- La plantilla debe utilizarse como base real del documento final[cite: 69].
- Deben respetarse todos los estilos definidos en la plantilla[cite: 69].
- La portada, introduccion, tablas, contenido y demas estructuras preexistentes deben reutilizarse cuando existan.
- No debe sustituirse la identidad documental de la plantilla por estilos arbitrarios.
- El archivo `.agents/templates/doc_base.docx`, o su equivalente en cada proyecto, debe quedar disponible para su utilizacion futura.
- La plantilla base no debe consumirse de manera destructiva ni quedar inutilizable tras la generacion.
- La automatizacion debe trabajar sobre copias o documentos derivados, preservando intacto el archivo base original.

## 8. CAMPOS DE FORMATO DEFINIDOS POR PLANTILLA Y AUTOMATIZACIÃ“N (CAMPOS DINÃMICOS)
- **Problema Conocido (Falta de ActualizaciÃ³n):** Es OBLIGATORIO asegurar que los campos dinÃ¡micos realmente se actualicen. Si los Controles de Contenido (Content Controls) de Word fallan, el script de Python debe buscar y reemplazar marcadores de texto (ej. `{{TITULO}}`), o modificar directamente las `core_properties` y el XML de las tablas del archivo DOCX.
- **Tabla de InformaciÃ³n del Documento (Llenado Obligatorio):** Queda estrictamente prohibido dejar campos vacÃ­os en la tabla frontal de control. El script DEBE inyectar los siguientes valores:
  - **Nombre del Proyecto:** El nombre exacto, SIEMPRE EN MAYÃšSCULAS (forzado vÃ­a `.upper()` en Python).
  - **Arquitecto de Software / Autor:** SIEMPRE debe ser "Gerardo Paiva G." (esto es un estÃ¡ndar absoluto). **InstrucciÃ³n para el script Python:** Se DEBE declarar estÃ¡ticamente en el cÃ³digo como `autor = "Gerardo Paiva G."` o inyectar el string literal directamente. Queda prohibido inferirlo del entorno o dejarlo parametrizable vacÃ­o.
  - **Fecha de AprobaciÃ³n:** Fecha actual o la indicada, estrictamente en formato DD/MM/YYYY.
  - **Estado del Documento:** Inyectar un estado coherente (ej. "Aprobado", "Borrador", "En RevisiÃ³n").
- El titulo debe actualizarse con el nombre oficial del proyecto documentado.
- El subtitulo debe actualizarse con el nombre especifico del documento (ej. "Acta de ConstituciÃ³n").
- El ano debe corresponder al periodo documental aplicable.
- Si estos campos ya existen en la plantilla como propiedades o controles, la automatizaciÃ³n debe modificarlos exitosamente o usar un mecanismo de reemplazo agresivo sobre el XML del DOCX para asegurar la inserciÃ³n de la data.

## 9. FORMATO OBLIGATORIO DE FECHAS
Toda fecha generada o actualizada en el documento debe usar obligatoriamente el formato DD/MM/YYYY.
Reglas aplicables a fechas:
- No usar formatos regionales alternos.
- No usar nombres de mes en texto.
- No usar formatos ISO dentro del contenido final del documento.
- Todas las fechas visibles del documento deben quedar normalizadas en formato DD/MM/YYYY.
- El historial de cambios tambien debe respetar obligatoriamente el formato DD/MM/YYYY.

## 10. HISTORIAL DE CAMBIOS OBLIGATORIO
Todo documento generado debe implementar un historial de cambios.
- **Ubicación Estricta:** El historial de cambios **DEBE quedar ubicado obligatoriamente debajo de la tabla de constitución del proyecto** (o la tabla frontal de control). El agente/script debe asegurar este orden exacto.
Reglas del historial de cambios:
- Debe existir una seccion formal de historial de cambios dentro del documento.
- Debe mantenerse como tabla o estructura claramente identificable.
- Debe generarse o actualizarse automaticamente desde el proceso en Python.
- Debe conservar consistencia entre versiones del mismo documento.
- Debe registrarse al menos la version inicial del documento cuando se genere por primera vez.
Contenido minimo del historial de cambios: Version, Fecha (DD/MM/YYYY), Autor o responsable (el cual SIEMPRE debe ser "Gerardo Paiva G." asignado de forma dura/estÃ¡tica en el script) y Descripcion del cambio realizado.
Reglas operativas:
- Si la plantilla ya incluye una seccion para control o historial de cambios, debe reutilizarse esa estructura.
- Si la plantilla no la incluye, debe incorporarse respetando los estilos y formato base.
- **ActualizaciÃ³n de Versiones (Append Row):** Si el documento ya existe, el script de Python DEBE localizar la tabla de historial de cambios (iterando por las tablas del DOCX) y usar `table.add_row()` para **AGREGAR una nueva fila** al final. Debe inyectar la versiÃ³n incrementada (ej. de v1.0 a v1.1), la fecha en formato DD/MM/YYYY, el autor ("Gerardo Paiva G.") y el detalle exacto del cambio.
- Queda estrictamente prohibido eliminar, vaciar o sobrescribir las filas anteriores del historial.

## 11. REGLAS DE FORMATO
- Deben usarse todos los estilos relevantes definidos en la plantilla base[cite: 69].
- Deben respetarse los campos estructurados de titulo, subtitulo, ano y fechas ya definidos por el formato[cite: 70].
- El contenido final debe quedar ortograficamente corregido en espanol tecnico, incluyendo tildes, acentuacion diacritica, signos y nombres propios documentales cuando corresponda.
- El indice o contenido debe mantenerse o actualizarse al finalizar la generacion cuando la plantilla lo contemple.
- La portada, introduccion, historial de cambios y secciones estructurales deben completarse segun el proposito del documento.

## 11.1. CORRECCION ORTOGRAFICA OBLIGATORIA
Todo documento generado debe pasar por una correccion ortografica antes de considerarse finalizado.
Reglas de correccion ortografica:
- Deben corregirse palabras sin tilde cuando la norma ortografica del espanol la requiera.
- Deben corregirse errores de acentuacion en terminos como introduccion, operacion, autorizacion, menu, jerarquia, politica, modulo, version, tecnico, diagnostico y terminos equivalentes.
- Debe mantenerse consistencia entre mayusculas, nombres de secciones y terminologia del dominio.
- No deben alterarse identificadores tecnicos, nombres de rutas, nombres de tablas, nombres de funciones, nombres de paquetes ni literales de codigo cuando formen parte de evidencia tecnica.
- La correccion ortografica debe aplicarse al Markdown fuente y reflejarse en el DOCX final generado.
- Si existen conflictos entre correccion ortografica y nomenclatura tecnica del sistema, debe prevalecer la nomenclatura tecnica dentro de fragmentos de codigo, rutas o nombres propios del repositorio.

## 11.2. FORMATO OBLIGATORIO DE TABLAS Y MECANISMO DE CONVERGENCIA
- El estilo de tabla obligatorio para este contexto es 'Tabla de lista 4 - Ã‰nfasis 1'[cite: 69]. La automatizacion en Python mediante la libreria python-docx debe invocar explicitamente este nombre de estilo: `table.style = 'Tabla de lista 4 - Ã‰nfasis 1'`.
- **Ancho Fijo y Formato Estricto para TODAS las tablas:** El script debe recorrer TODAS Y CADA UNA de las tablas del documento (ya sean nuevas o preexistentes), y forzar obligatoriamente que su estilo sea el definido y que su ancho total quede configurado exactamente en **17 cm**. No se permite ninguna tabla con tamaño o formato diferente.
- Mecanismo de Fallback Controlado: Si el script de Python detecta mediante la inspeccion del catalogo de estilos del archivo '.agents/templates/doc_base.docx' que dicho estilo no se encuentra declarado, la automatizacion aplicara el estilo nativo estructurado mas cercano (ej. 'Light Shading Accent 1') y registrara una advertencia explicita en el log de ejecuciÃ³n del sistema, evitando que el proceso aborte por inconsistencia visual o errores de ejecucion en la libreria.
- El estilo de tabla debe aplicarse tanto a tablas de contenido tecnico como a tablas de control de cambios, matrices, catalogos y resumenes documentales, salvo que la propia plantilla defina una excepcion estructural.

## 11.3. REGLAS DE ESPACIADO Y SALTOS DE PÁGINA
- **Salto de Línea Post-Título:** Siempre debe existir obligatoriamente un salto de línea (párrafo vacío o espaciado equivalente) entre cualquier título/subtítulo y el párrafo de texto que le sigue.
- **Salto de Página Pre-Título:** Cuando se inicie un nuevo título principal (nueva sección mayor), se debe insertar obligatoriamente un salto de página (`page break`) ANTES del título, para que arranque en una página limpia.

## 11.4. ACTUALIZACIÓN DE TABLA DE CONTENIDOS (TOC)
- **El paso final ineludible:** Lo último que se debe hacer antes de finalizar y guardar cada documento es **actualizar la tabla de contenidos (TOC)**. El script o el agente debe asegurar que el índice refleje correctamente la nueva estructura y paginación producto de los saltos de página añadidos.

## 12. REGLAS DE FIDELIDAD TÉCNICA Y PATRONES ENTERPRISE
- **Detalle a Nivel de CÃ³digo (Profundidad MÃ¡xima):** La documentaciÃ³n no debe ser solo conceptual. Debe reflejar exactamente los DTOs, entidades, relaciones de base de datos, mÃ©todos crÃ­ticos y flujos de eventos que existan en el cÃ³digo (o en la ideaciÃ³n si es Fase 0). Un documento es invÃ¡lido si parece un "esqueleto" genÃ©rico que podrÃ­a aplicar a cualquier proyecto.
- **DefiniciÃ³n Estricta de Casos de Uso:** Es obligatorio documentar los Casos de Uso del sistema. No basta con listarlos; cada Caso de Uso debe estar profundamente definido incluyendo: Nombre, Actor principal, Precondiciones, Flujo Principal (paso a paso), Flujos Alternativos/Excepciones, y Postcondiciones.
- No se deben documentar aplicaciones moviles, de escritorio, servicios externos, colas, integraciones o modulos no presentes, salvo evidencia directa[cite: 18, 37].
- **Patrones Enterprise (Obligatorio):** Si la IA detecta que el repositorio implementa CQRS (Commands vs Queries), Eventos de Dominio (Event-Driven Architecture) o PatrÃ³n Outbox, debe documentarlos explÃ­citamente en el "DiseÃ±o de Arquitectura de Software".
- **Feature Flags:** De documentarse la gestiÃ³n de despliegues, debe rastrearse e incluirse el catÃ¡logo de Feature Flags / Toggles si estos existen en el cÃ³digo.
- Si un componente es opcional, alternativo, heredado o experimental, debe marcarse como tal.
- Si un componente no aplica al proyecto, debe omitirse o declararse como no aplicable.
- Si existe mas de una implementacion posible dentro del repositorio, debe diferenciarse claramente entre principal y secundaria.

## 13. DIAGRAMAS, TABLAS Y RECURSOS VISUALES
- Los diagramas deben generarse automaticamente cuando aporten valor explicativo[cite: 75].
- Deben reflejar relaciones reales entre componentes, modulos, servicios, capas o datos[cite: 75].
- Las tablas deben usarse para sintetizar informacion verificable[cite: 74].
- Todo recurso visual debe responder al contenido tecnico del proyecto y no ser decorativo[cite: 75].

## 14. ESTRUCTURA DE ENTREGABLES
Para cada documento deben mantenerse, cuando corresponda: Fuente en Markdown u otro formato intermedio estructurado, script Python generador, imagenes o diagramas generados, documento final en DOCX y recursos auxiliares necesarios para su regeneracion. La salida debe almacenarse en la ruta definida para la documentacion del proyecto.

## 15. VALIDACION MINIMA OBLIGATORIA
Antes de considerar un documento como generado correctamente, debe validarse que: El archivo final exista, el script Python no tenga errores de sintaxis, la plantilla base haya sido reutilizada, el titulo, subtitulo, ano y fechas se hayan completado conforme a la plantilla, el historial de cambios exista con contenido valido, las tablas usen el estilo 'Tabla de lista 4 - Ã‰nfasis 1' o su alternativa controlada, el contenido haya pasado la correccion ortografica, y no queden marcas crudas de Markdown ni texto sin procesar en el resultado final[cite: 70].

## 16. PRESERVACION DE ACTIVOS DOCUMENTALES
- La plantilla base DOCX debe preservarse.
- Los scripts deben mantenerse reutilizables[cite: 78].
- Las imagenes generadas deben guardarse de forma ordenada[cite: 77].
- La estructura documental debe permitir continuidad entre documentos futuros.
- Los activos documentales deben quedar listos para nuevas iteraciones del proyecto sin rehacer la base de trabajo[cite: 78].

## 17. RESULTADO ESPERADO
El resultado esperado es un conjunto de documentos tecnicos generados por ingenieria inversa, consistentes entre si, sustentados por evidencia real del proyecto, automatizados con Python y alineados con una plantilla base reutilizable que permanezca disponible para usos posteriores[cite: 43, 53].

## 18. CLAUSULA OPERATIVA CONSOLIDADA
La automatizacion documental debe generar cada documento reutilizando la plantilla base disponible, respetando sus estilos y sus campos estructurados predefinidos de titulo, subtitulo, ano y fechas, preservando el archivo base para uso futuro e implementando obligatoriamente un historial de cambios mantenible, regenerable y normalizado en formato DD/MM/YYYY[cite: 70].
