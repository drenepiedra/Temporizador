# Guía de Distribución - Temporizador de Concentración

Esta guía explica cómo distribuir y ejecutar tu aplicación en cualquier ordenador.

## 📦 Paso 1: Compilación

Asegúrate de que tu proyecto está compilado:

```powershell
# En Windows PowerShell
.\build.ps1
```

Esto generará el archivo: `target/TimerCircle-1.0.0.jar`

## 🚚 Paso 2: Distribución del Ejecutable

### Opción A: Distribuir solo el JAR (Simple)

El archivo `target/TimerCircle-1.0.0.jar` es todo lo que necesitan los usuarios.

**Ventajas:**
- Archivo pequeño (~60 KB)
- Funciona en cualquier SO con Java

**Desventajas:**
- Requiere que el usuario tenga Java instalado

### Opción B: Crear un Instalador Windows (Avanzado)

Necesitarás crear un ejecutable `.exe` que incluya Java empaquetado:

#### Método 1: Usar Launch4j (Recomendado)

1. Descarga Launch4j: https://launch4j.sourceforge.net/
2. Crea una configuración:
   - Selecciona el JAR compilado
   - Especifica el ícono
   - Configura la JVM mínima (11+)
3. Genera el `.exe`

#### Método 2: Usar JPackage (Java 16+)

```powershell
# Requiere JDK 16 o superior
jpackage --input target `
          --name "Temporizador" `
          --main-jar TimerCircle-1.0.0.jar `
          --main-class TimerCircle `
          --type exe `
          --app-version 1.0.0 `
          --win-menu `
          --win-shortcut
```

### Opción C: Crear Instalador con NSIS (Windows)

1. Descarga NSIS: https://nsis.sourceforge.io/
2. Crea un script `.nsi`:

```nsis
!include "MUI2.nsh"
!include "x64.nsh"

Name "Temporizador de Concentración"
OutFile "TimerCircle-Installer-1.0.0.exe"
InstallDir "$PROGRAMFILES\Temporizador"

!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_LANGUAGE "Spanish"

Section "Instalar"
    SetOutPath "$INSTDIR"
    File "target\TimerCircle-1.0.0.jar"
    
    CreateDirectory "$SMPROGRAMS\Temporizador"
    CreateShortcut "$SMPROGRAMS\Temporizador\Temporizador.lnk" \
                   "javaw.exe" \
                   "-jar $INSTDIR\TimerCircle-1.0.0.jar"
    CreateShortcut "$DESKTOP\Temporizador.lnk" \
                   "javaw.exe" \
                   "-jar $INSTDIR\TimerCircle-1.0.0.jar"
SectionEnd

Section "Desinstalar"
    Delete "$SMPROGRAMS\Temporizador\Temporizador.lnk"
    Delete "$DESKTOP\Temporizador.lnk"
    RMDir "$SMPROGRAMS\Temporizador"
    Delete "$INSTDIR\TimerCircle-1.0.0.jar"
    RMDir "$INSTDIR"
SectionEnd
```

3. Compila con NSIS

## 🎯 Paso 3: Instrucciones para Usuarios Finales

### Para ejecutar la aplicación:

**Opción 1: Si tienes Java instalado**
```bash
java -jar TimerCircle-1.0.0.jar
```

**Opción 2: Windows (doble clic)**
Crea un archivo `run.bat` en la misma carpeta que el JAR:

```batch
@echo off
java -jar TimerCircle-1.0.0.jar
pause
```

Los usuarios pueden hacer doble clic en `run.bat` para ejecutar la aplicación.

**Opción 3: macOS/Linux**
Crea un archivo `run.sh` con permisos de ejecución:

```bash
#!/bin/bash
java -jar TimerCircle-1.0.0.jar
```

```bash
chmod +x run.sh
./run.sh
```

## 🔧 Requisitos Mínimos para Usuarios

- **Java Runtime Environment (JRE) 11 o superior**
  - Descargar: https://www.java.com/es/download/
  - O verificar en terminal: `java -version`

## 📋 Checklist de Distribución

- [ ] Proyecto compilado exitosamente con `mvn clean package`
- [ ] Archivo `TimerCircle-1.0.0.jar` generado en `target/`
- [ ] Probado localmente: `java -jar target/TimerCircle-1.0.0.jar`
- [ ] README.md actualizado con instrucciones
- [ ] Scripts (`run.ps1`, `run.sh`, `run.bat`) creados
- [ ] Versión del código fuente guardada en Git
- [ ] Preparado para distribuir

## 📤 Cómo Compartir

1. **Por correo electrónico:**
   - Adjunta `TimerCircle-1.0.0.jar`
   - Incluye instrucciones de requisitos

2. **Por Google Drive/Dropbox:**
   - Sube el JAR
   - Comparte el enlace
   - Abre acceso público

3. **Por GitHub:**
   ```bash
   git push origin main
   # Los usuarios pueden descargar el código y compilar
   # O descargar el JAR compilado desde Releases
   ```

4. **Como descarga web:**
   - Usa un servicio como GitHub Releases
   - O aloja en tu propio servidor

## 🔐 Notas de Seguridad

- Firma el JAR si lo distribuyes
- Proporciona hash SHA-256 para verificación
- Usa HTTPS para descargas en línea
- Informa a usuarios sobre requisitos de Java

## 📞 Soporte

Proporciona instrucciones de instalación de Java para usuarios sin experiencia:

**Windows:**
1. Visita https://www.java.com/es/download/
2. Descarga "Java Runtime Environment"
3. Ejecuta el instalador
4. Reinicia el ordenador
5. Verifica: abre PowerShell y escribe `java -version`

**macOS:**
```bash
# Usando Homebrew
brew install openjdk@11
```

**Linux:**
```bash
# Ubuntu/Debian
sudo apt-get install openjdk-11-jre

# Fedora
sudo dnf install java-11-openjdk
```

## 🎉 ¡Listo!

Tu aplicación puede ser distribuida a usuarios sin que necesiten compilar código.
