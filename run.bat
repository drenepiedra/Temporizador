@echo off
REM Script para ejecutar el Temporizador
REM Requiere: Java Runtime Environment (JRE) instalado

setlocal enabledelayedexpansion

echo.
echo === Ejecutando Temporizador de Concentración ===
echo.

REM Verifica si Java está instalado
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java no está instalado o no está en PATH
    echo.
    echo Descargalo desde: https://www.java.com/es/download/
    echo.
    pause
    exit /b 1
)

REM Verifica si el JAR existe
if not exist "target\TimerCircle-1.0.0.jar" (
    echo ERROR: No se encuentra el archivo compilado
    echo.
    echo Por favor, ejecuta primero: build.bat
    echo.
    pause
    exit /b 1
)

echo Iniciando aplicación...
echo.

java -jar target\TimerCircle-1.0.0.jar

pause
