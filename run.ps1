# Script para ejecutar el Temporizador
# Requiere: Java Runtime Environment (JRE) instalado

Write-Host "=== Ejecutando Temporizador de Concentración ===" -ForegroundColor Green

# Verifica si Java está instalado
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Host "ERROR: Java no está instalado o no está en PATH" -ForegroundColor Red
    Write-Host "Descargalo desde: https://www.java.com/es/download/" -ForegroundColor Yellow
    exit 1
}

# Verifica si el JAR existe
if (-not (Test-Path "target/TimerCircle-1.0.0.jar")) {
    Write-Host "ERROR: No se encuentra el archivo compilado" -ForegroundColor Red
    Write-Host "Por favor, ejecuta primero: .\build.ps1" -ForegroundColor Yellow
    exit 1
}

Write-Host "Iniciando aplicación..." -ForegroundColor Cyan
java -jar target/TimerCircle-1.0.0.jar
