#!/bin/bash
# Script para ejecutar el Temporizador
# Requiere: Java Runtime Environment (JRE) instalado

echo "=== Ejecutando Temporizador de Concentración ==="

# Verifica si Java está instalado
if ! command -v java &> /dev/null; then
    echo "ERROR: Java no está instalado o no está en PATH"
    echo "Descargalo desde: https://www.java.com/es/download/"
    exit 1
fi

# Verifica si el JAR existe
if [ ! -f "target/TimerCircle-1.0.0.jar" ]; then
    echo "ERROR: No se encuentra el archivo compilado"
    echo "Por favor, ejecuta primero: ./build.sh"
    exit 1
fi

echo "Iniciando aplicación..."
java -jar target/TimerCircle-1.0.0.jar
