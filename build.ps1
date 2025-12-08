# Script de construcción para Temporizador Circle
# Requiere: Maven instalado (https://maven.apache.org/download.cgi)

Write-Host "=== Compilando Temporizador de Concentración ===" -ForegroundColor Green

# Verifica si Maven está instalado
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "ERROR: Maven no está instalado o no está en PATH" -ForegroundColor Red
    Write-Host "Descargalo desde: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
    exit 1
}

# Limpia y compila
Write-Host "Limpiando build anterior..." -ForegroundColor Cyan
mvn clean

Write-Host "Compilando aplicación..." -ForegroundColor Cyan
mvn package

if ($LASTEXITCODE -eq 0) {
    Write-Host "`n✓ ¡Compilación exitosa!" -ForegroundColor Green
    Write-Host "`nEl ejecutable se encuentra en:" -ForegroundColor Green
    Write-Host "  target/TimerCircle-1.0.0.jar" -ForegroundColor Yellow
    Write-Host "`nPara ejecutar:" -ForegroundColor Cyan
    Write-Host "  java -jar target/TimerCircle-1.0.0.jar" -ForegroundColor White
} else {
    Write-Host "`n✗ Error en la compilación" -ForegroundColor Red
    exit 1
}
