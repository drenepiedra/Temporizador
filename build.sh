#!/bin/bash
# Script de construcción para Temporizador Circle
# Requiere: Maven instalado (https://maven.apache.org/download.cgi)

echo "=== Compilando Temporizador de Concentración ==="

# Verifica si Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven no está instalado o no está en PATH"
    echo "Descargalo desde: https://maven.apache.org/download.cgi"
    exit 1
fi

# Limpia y compila
echo "Limpiando build anterior..."
mvn clean

echo "Compilando aplicación..."
mvn package

if [ $? -eq 0 ]; then
    echo ""
    echo "✓ ¡Compilación exitosa!"
    echo ""
    echo "El ejecutable se encuentra en:"
    echo "  target/TimerCircle-1.0.0.jar"
    echo ""
    echo "Para ejecutar:"
    echo "  java -jar target/TimerCircle-1.0.0.jar"
else
    echo ""
    echo "✗ Error en la compilación"
    exit 1
fi
