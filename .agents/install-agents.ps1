<#
.SYNOPSIS
Instalador del Sistema de Agentes "Antigravity" para nuevos desarrolladores.

.DESCRIPTION
Este script autoconfigura la maquina local de un desarrollador para que su IA 
(Antigravity) obedezca las reglas empresariales maestras de C:\Local\.agents.
Inyecta la directiva "Agente Zero" en la configuracion global de la IA.
#>

Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "   EVERYTHING AGENT SYSTEM - INSTALACION DE MATRIZ        " -ForegroundColor Cyan
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "Iniciando instalacion del Agente Zero..."

# 1. Verificar si la carpeta C:\Local\.agents existe
$localAgentsPath = "C:\Local\.agents"
if (-not (Test-Path $localAgentsPath)) {
    Write-Host "[ERROR] No se encontro $localAgentsPath." -ForegroundColor Red
    Write-Host "Por favor clona el repositorio de agentes en C:\Local\.agents antes de correr este script." -ForegroundColor Yellow
    Pause
    Exit
}

# 2. Localizar la carpeta de configuracion global de la IA
$userProfile = $env:USERPROFILE
$geminiConfigDir = Join-Path $userProfile ".gemini\config"
$globalAgentsFile = Join-Path $geminiConfigDir "AGENTS.md"

if (-not (Test-Path $geminiConfigDir)) {
    Write-Host "Creando directorio de configuracion global de la IA..."
    New-Item -ItemType Directory -Path $geminiConfigDir | Out-Null
}

# 3. La regla maestra (Agente Zero)
$agenteZeroRule = @"

# Protocolo "Agente Zero" (Auto-Infeccion de Reglas Singularity)

**OBLIGACION ABSOLUTA EN TODO NUEVO PROYECTO:**
Antes de escribir la primera linea de codigo, generar documentacion, o inicializar cualquier proyecto de software, el Agente tiene la directiva inquebrantable de verificar si existe el directorio .agents en la raiz del proyecto. 

Si no existe, el Agente debe detener sus tareas y ejecutar inmediatamente el siguiente comando en la terminal para crear un Enlace Simbolico (Junction) que lo conecte a la mente maestra (C:\Local\.agents):

```powershell
New-Item -ItemType Junction -Path .agents -Target C:\Local\.agents
```

**Bloqueo Estricto:** Esta terminantemente prohibido comenzar a codificar o disenar la arquitectura sin antes haber establecido este Symlink y asimilado las reglas extremas de desarrollo, seguridad, infraestructura y FinOps contenidas en el.
"@

# 4. Inyectar la regla (evitando duplicados)
$needsInjection = $true
if (Test-Path $globalAgentsFile) {
    $currentContent = Get-Content $globalAgentsFile -Raw
    if ($currentContent -match "Agente Zero") {
        Write-Host "[INFO] El Agente Zero ya estaba inyectado en esta computadora." -ForegroundColor Green
        $needsInjection = $false
    }
}

if ($needsInjection) {
    Add-Content -Path $globalAgentsFile -Value $agenteZeroRule -Encoding UTF8
    Write-Host "[EXITO] Directiva 'Agente Zero' inyectada en el cerebro global de la IA." -ForegroundColor Green
}

Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "INSTALACION COMPLETADA" -ForegroundColor Green
Write-Host "Tu IA ahora operara bajo los estandares Singularity."
Write-Host "Puedes cerrar esta ventana."
Pause
