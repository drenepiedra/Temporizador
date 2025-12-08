# Script de compilación sin Maven (solo JDK)
# Compila TimerCircle.java directamente a JAR ejecutable

Write-Host "=== Compilando Temporizador de Concentración ===" -ForegroundColor Green

# Verifica si el JDK está instalado
if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    Write-Host "ERROR: Java Development Kit (JDK) no está instalado" -ForegroundColor Red
    Write-Host "Descargalo desde: https://www.oracle.com/java/technologies/downloads/" -ForegroundColor Yellow
    Read-Host "Presiona Enter para cerrar"
    exit 1
}

# Crea directorios
Write-Host "Preparando directorios..." -ForegroundColor Cyan
$targetDir = "target"
$classesDir = "target/classes"
$metaInfDir = "target/META-INF"

if (-not (Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
}
if (-not (Test-Path $classesDir)) {
    New-Item -ItemType Directory -Path $classesDir -Force | Out-Null
}
if (-not (Test-Path $metaInfDir)) {
    New-Item -ItemType Directory -Path $metaInfDir -Force | Out-Null
}

# Compila el código Java
Write-Host "Compilando código fuente..." -ForegroundColor Cyan
javac -d target/classes -encoding UTF-8 TimerCircle.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "`n✗ Error en la compilación" -ForegroundColor Red
    Read-Host "Presiona Enter para cerrar"
    exit 1
}

# Crea el MANIFEST
Write-Host "Creando manifest..." -ForegroundColor Cyan
$manifest = @"
Manifest-Version: 1.0
Main-Class: TimerCircle

"@
Set-Content -Path "target/META-INF/MANIFEST.MF" -Value $manifest -Encoding ASCII

# Crea el JAR usando PowerShell (ZIP)
Write-Host "Empaquetando JAR..." -ForegroundColor Cyan
$jarPath = "target/TimerCircle-1.0.0.jar"
if (Test-Path $jarPath) {
    Remove-Item $jarPath
}

# Usamos el .NET Framework para crear un ZIP (que es un JAR)
Add-Type -AssemblyName System.IO.Compression.FileSystem
[System.IO.Compression.ZipFile]::CreateFromDirectory((Get-Item $classesDir).FullName, (Get-Item $targetDir).FullName + "\TimerCircle-1.0.0.jar")

Write-Host "`n✓ ¡Compilación exitosa!" -ForegroundColor Green
Write-Host "`nEl ejecutable se encuentra en:" -ForegroundColor Green
Write-Host "  target/TimerCircle-1.0.0.jar" -ForegroundColor Yellow
Write-Host "`nPara ejecutar:" -ForegroundColor Cyan
Write-Host "  java -jar target/TimerCircle-1.0.0.jar" -ForegroundColor White
Write-Host "  O simplemente: .\run.ps1" -ForegroundColor White
Write-Host ""
