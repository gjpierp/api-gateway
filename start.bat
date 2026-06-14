@echo off
REM ==========================================
REM Script para iniciar el proyecto API Gateway utilizando Docker Compose.
REM
REM Este script invoca al script start.ps1
REM ==========================================

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0start.ps1"
pause
