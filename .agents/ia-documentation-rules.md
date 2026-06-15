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
- Generar cada documento por separado, con contenido completo e independiente.
- Aplicar correccion ortografica obligatoria al contenido documental antes de generar el entregable final.
- Utilizar automatizacion total con Python para producir fuentes, imagenes, tablas y documentos finales[cite: 43].
- Reutilizar la plantilla base DOCX cuando exista, respetando sus estilos, campos estructurados y secciones predefinidas[cite: 69].
- Aplicar a las tablas documentales el estilo exacto 'Tabla de lista 4 - Énfasis 1' cuando ese formato este definido en la plantilla base[cite: 69].
- Mantener disponible el archivo doc_base.docx, o su equivalente, para reutilizacion posterior.
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
- La IA puede omitir un documento solo si no aplica al proyecto y debe dejar esa condicion explicitamente justificada.
- Cada documento debe generarse como entregable independiente, con su propio archivo fuente, script generador y salida final.

## 1. OBJETIVO
Establecer un estandar reutilizable para la generacion de documentacion tecnica exhaustiva, detallada e individual de cualquier proyecto de software, mediante ingenieria inversa del repositorio, automatizacion total con Python y uso controlado de una plantilla documental base en formato DOCX cuando exista[cite: 43, 53].

## 2. ALCANCE
Este estandar aplica a cualquier proyecto que requiera documentacion tecnica estructurada a partir de su codigo fuente, configuracion, infraestructura, dependencias, contratos, scripts y artefactos asociados[cite: 43].
Aplica a documentos funcionales, tecnicos, arquitectonicos, de datos, integracion, operacion, seguridad, despliegue, trazabilidad y cualquier otro entregable documental requerido por el proyecto.

## 3. PRINCIPIOS RECTORES
- La documentacion debe generarse a partir de evidencia real del proyecto[cite: 71].
- Cada documento debe construirse de forma individual e independiente.
- El contenido debe ser exhaustivo y no resumido artificialmente.
- La automatizacion debe realizarse integralmente con Python[cite: 43].
- La documentacion final debe ser regenerable, mantenible y consistente[cite: 78].
- No deben inventarse componentes, tecnologias, modulos o integraciones no presentes en el repositorio[cite: 37].

## 4. FUENTE OFICIAL DE INFORMACION
La fuente de verdad para la documentacion sera la evidencia observable en el proyecto, incluyendo: Codigo fuente, archivos de configuracion, dependencias declaradas, Dockerfiles, archivos compose, variables de entorno, scripts de despliegue, estructura de base de datos, objetos persistentes, contratos API, rutas, controladores, middlewares y artefactos de infraestructura asociados[cite: 4, 9, 18, 33]. Toda afirmacion incluida en la documentacion debe poder vincularse a una evidencia tecnica real del repositorio[cite: 71].

## 5. REGLAS DE GENERACION DOCUMENTAL
- Cada documento se debe generar por separado.
- No se deben fusionar varios documentos en un solo entregable, salvo instruccion explicita.
- Cada documento debe tener contenido completo, portada, introduccion, historial de cambios, desarrollo, tablas, diagramas y cierre si corresponde.
- El contenido debe mantener consistencia terminologica y tecnica entre todos los documentos del conjunto.
- El nombre del proyecto debe respetarse exactamente como corresponda al repositorio analizado o a la instruccion formal recibida.

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
- El archivo doc_base.docx, o su equivalente en cada proyecto, debe quedar disponible para su utilizacion futura.
- La plantilla base no debe consumirse de manera destructiva ni quedar inutilizable tras la generacion.
- La automatizacion debe trabajar sobre copias o documentos derivados, preservando intacto el archivo base original.

## 8. CAMPOS DE FORMATO DEFINIDOS POR PLANTILLA Y AUTOMATIZACIÓN
- Los campos estructurales de Título, Subtítulo, Año y Fechas deben ser actualizados programáticamente por el script de Python sobre los controles de contenido o propiedades del documento base original[cite: 70].
- El titulo debe actualizarse con el nombre oficial del proyecto documentado.
- El subtitulo debe actualizarse con el nombre especifico del documento generado.
- El ano debe corresponder al periodo documental aplicable al proyecto o al documento.
- Las fechas deben actualizarse segun el contexto documental requerido, por ejemplo emision, actualizacion, aprobacion o registro del cambio.
- Si estos campos ya existen en la plantilla como propiedades, controles de contenido o campos automaticos, deben actualizarse sobre esa misma estructura[cite: 70].
- No deben duplicarse como texto libre si ya estan definidos estructuralmente en la plantilla[cite: 70].
- La automatizacion debe preservar el formato visual y funcional original del documento base[cite: 70].

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
Reglas del historial de cambios:
- Debe existir una seccion formal de historial de cambios dentro del documento.
- Debe mantenerse como tabla o estructura claramente identificable.
- Debe generarse o actualizarse automaticamente desde el proceso en Python.
- Debe conservar consistencia entre versiones del mismo documento.
- Debe registrarse al menos la version inicial del documento cuando se genere por primera vez.
Contenido minimo del historial de cambios: Version, Fecha, Autor o responsable y Descripcion del cambio realizado.
Reglas operativas:
- Si la plantilla ya incluye una seccion para control o historial de cambios, debe reutilizarse esa estructura.
- Si la plantilla no la incluye, debe incorporarse respetando los estilos y formato base.
- No debe eliminarse el historial previo al regenerar el documento, salvo instruccion explicita.
- Las nuevas ejecuciones deben poder agregar o actualizar entradas de manera consistente.

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
- El estilo de tabla obligatorio para este contexto es 'Tabla de lista 4 - Énfasis 1'[cite: 69]. La automatizacion en Python mediante la libreria python-docx debe invocar explicitamente este nombre de estilo: `table.style = 'Tabla de lista 4 - Énfasis 1'`.
- Mecanismo de Fallback Controlado: Si el script de Python detecta mediante la inspeccion del catalogo de estilos del archivo 'doc_base.docx' que dicho estilo no se encuentra declarado, la automatizacion aplicara el estilo nativo estructurado mas cercano (ej. 'Light Shading Accent 1') y registrara una advertencia explicita en el log de ejecución del sistema, evitando que el proceso aborte por inconsistencia visual o errores de ejecucion en la libreria.
- El estilo de tabla debe aplicarse tanto a tablas de contenido tecnico como a tablas de control de cambios, matrices, catalogos y resumenes documentales, salvo que la propia plantilla defina una excepcion estructural.

## 12. REGLAS DE FIDELIDAD TECNICA
- No se deben documentar aplicaciones moviles, de escritorio, servicios externos, colas, integraciones o modulos no presentes, salvo evidencia directa[cite: 18, 37].
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
Antes de considerar un documento como generado correctamente, debe validarse que: El archivo final exista, el script Python no tenga errores de sintaxis, la plantilla base haya sido reutilizada, el titulo, subtitulo, ano y fechas se hayan completado conforme a la plantilla, el historial de cambios exista con contenido valido, las tablas usen el estilo 'Tabla de lista 4 - Énfasis 1' o su alternativa controlada, el contenido haya pasado la correccion ortografica, y no queden marcas crudas de Markdown ni texto sin procesar en el resultado final[cite: 70].

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