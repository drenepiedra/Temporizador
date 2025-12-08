@echo off
REM Compilador directo sin Maven
REM Genera el JAR ejecutable directamente usando javac y jar

setlocal enabledelayedexpansion

echo.
echo === Compilando Temporizador de Concentración (Modo Directo) ===
echo.

REM Verifica si Java está instalado
where javac >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java Development Kit (JDK) no está instalado
    echo.
    echo Descargalo desde: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

REM Crea el directorio de salida
if not exist "target\classes" mkdir target\classes

REM Compila el código Java
echo Compilando código fuente...
javac -d target\classes -encoding UTF-8 TimerCircle.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ✗ Error en la compilación
    echo.
    pause
    exit /b 1
)

REM Crea el archivo MANIFEST
if not exist "target\META-INF" mkdir target\META-INF
(
    echo Manifest-Version: 1.0
    echo Main-Class: TimerCircle
    echo.
) > target\META-INF\MANIFEST.MF

REM Crea el JAR ejecutable
echo Creando JAR ejecutable...
cd target\classes
jar cfm ..\TimerCircle-1.0.0.jar ..\META-INF\MANIFEST.MF *
cd ..\..

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
    echo ✗ Error al crear el JAR
    echo.
    pause
    exit /b 1
)

pause
