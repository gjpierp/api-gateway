import sys

with open('C:/Local/.agents/ia-documentation-rules.md', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Update Reglas de actualizacion (No sobrescribir)
old_rule1 = """- **Regla de Actualización Incremental (No Sobrescribir y Preservar Portada/Intro):** Si el archivo (DOCX) ya existe (os.path.exists), el script **TIENE ESTRICTAMENTE PROHIBIDO** crear un Document() nuevo y vacío. Debe instanciar Document(file_path) para abrir el existente. Además:"""
new_rule1 = """- **REGLA DE ACTUALIZACIÓN INCREMENTAL (PROHIBIDO RECREAR/SOBREESCRIBIR TOTALMENTE):** La IA y los scripts Tienen **ESTRICTAMENTE PROHIBIDO** destruir y volver a crear los documentos si estos ya existen. Deben abrir el archivo existente (Document(file_path)), actualizar EXCLUSIVAMENTE las secciones nuevas o modificadas, y preservar el resto (incluyendo portadas e introducciones). Borrar y hacer de nuevo los documentos es una falta crítica. Además:"""
content = content.replace(old_rule1, new_rule1)

# 2. Update Table rule
old_rule2 = """- **Ancho Fijo Obligatorio:** Por defecto, TODAS las tablas generadas o modificadas por el script deben ajustarse a un ancho total de **17 cm** para alinearse con los márgenes del documento base. (En python-docx esto implica usar docx.shared.Cm(17) en el ancho total de la tabla)."""
new_rule2 = """- **Ancho Fijo y Formato Estricto para TODAS las tablas:** El script debe recorrer TODAS Y CADA UNA de las tablas del documento (ya sean nuevas o preexistentes), y forzar obligatoriamente que su estilo sea el definido y que su ancho total quede configurado exactamente en **17 cm**. No se permite ninguna tabla con tamaño o formato diferente."""
content = content.replace(old_rule2, new_rule2)

# 3. Update Historial de Cambios location
old_rule3 = """Todo documento generado debe implementar un historial de cambios."""
new_rule3 = """Todo documento generado debe implementar un historial de cambios.
- **Ubicación Estricta:** El historial de cambios **DEBE quedar ubicado obligatoriamente debajo de la tabla de constitución del proyecto** (o la tabla frontal de control). El agente/script debe asegurar este orden exacto."""
content = content.replace(old_rule3, new_rule3)

# 4 & 5. Insert Spacing, Page Break and TOC rules before section 12
old_rule4 = """## 12. REGLAS DE FIDELIDAD TÉCNICA Y PATRONES ENTERPRISE"""
new_rule4 = """## 11.3. REGLAS DE ESPACIADO Y SALTOS DE PÁGINA
- **Salto de Línea Post-Título:** Siempre debe existir obligatoriamente un salto de línea (párrafo vacío o espaciado) entre un título/subtítulo y el párrafo de texto que le sigue.
- **Salto de Página Pre-Título:** Cuando se inicie un nuevo título principal (nueva sección mayor), se debe insertar obligatoriamente un salto de página (page break) ANTES del título, para que arranque en una página nueva.

## 11.4. ACTUALIZACIÓN DE TABLA DE CONTENIDOS (TOC)
- **El paso final ineludible:** Lo último que se debe hacer antes de finalizar y guardar cada documento es **actualizar la tabla de contenidos**. El script o el agente debe asegurar que el índice refleje correctamente la nueva estructura y paginación producto de los saltos de página añadidos.

## 12. REGLAS DE FIDELIDAD TÉCNICA Y PATRONES ENTERPRISE"""
content = content.replace(old_rule4, new_rule4)

with open('C:/Local/.agents/ia-documentation-rules.md', 'w', encoding='utf-8') as f:
    f.write(content)

print("Rules updated successfully.")
