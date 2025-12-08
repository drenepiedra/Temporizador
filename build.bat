@echo off
REM Script de compilación para Temporizador Circle
REM Requiere: Maven instalado (https://maven.apache.org/download.cgi)

setlocal enabledelayedexpansion

echo.
echo === Compilando Temporizador de Concentración ===
echo.

REM Verifica si Maven está instalado
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven no está instalado o no está en PATH
    echo.
    echo Descargalo desde: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

REM Limpia y compila
echo Limpiando build anterior...
call mvn clean

echo.
echo Compilando aplicación...
call mvn package

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✓ ¡Compilación exitosa!
    echo.
    echo El ejecutable se encuentra en:
    echo   target\TimerCircle-1.0.0.jar
    echo.
    echo Para ejecutar:
    echo   java -jar target\TimerCircle-1.0.0.jar
    echo.
) else (
    echo.
    echo ✗ Error en la compilación
    echo.
    exit /b 1
)

pause
