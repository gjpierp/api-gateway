<#
.SYNOPSIS
Script para iniciar el proyecto API Gateway utilizando Docker Compose.

.DESCRIPTION
Este script detiene los contenedores existentes (si los hay), reconstruye la imagen del API Gateway
utilizando el Dockerfile multi-etapa y levanta los servicios definidos en el docker-compose.yml.

.NOTES
Autor: AI Assistant
Fecha: 2026-06-05
#>

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "Iniciando despliegue de API Gateway..." -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

# Comprobar si Docker está en ejecución
docker info > $null 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Docker no está en ejecución. Por favor, inicie Docker Desktop y vuelva a intentarlo." -ForegroundColor Red
    exit 1
}

Write-Host "`n[1/3] Deteniendo y limpiando contenedores anteriores..." -ForegroundColor Yellow
docker-compose down

Write-Host "`n[2/3] Construyendo la imagen del API Gateway..." -ForegroundColor Yellow
docker-compose build --no-cache

Write-Host "`n[3/3] Levantando los servicios en segundo plano..." -ForegroundColor Yellow
docker-compose up -d

Write-Host "`nDespliegue completado." -ForegroundColor Green
Write-Host "Puede ver los logs ejecutando: docker-compose logs -f" -ForegroundColor Cyan
Write-Host "El servicio estará disponible en http://localhost:8080 (dependiendo del healthcheck)" -ForegroundColor Cyan
