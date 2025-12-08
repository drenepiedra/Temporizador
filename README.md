# Temporizador de Concentración (Java Swing)

Aplicación de escritorio multiplataforma para medir tiempos de concentración con temporizador visual circular.

## 🎯 Características

- ⏱️ **Temporizador visual circular** - Representación gráfica del tiempo restante
- 🎨 **Interfaz intuitiva** - Fácil de usar y personalizable
- 📌 **Ventana siempre encima** - Toggle para mantener la ventana visible
- ⏸️ **Control completo** - Iniciar, pausar y reiniciar
- 🔔 **Sonido de alerta** - Notificación cuando termina el tiempo
- 💻 **Multiplataforma** - Funciona en Windows, macOS y Linux

## 📋 Requisitos Previos

### Para ejecutar la aplicación:
- **Java Runtime Environment (JRE) 11 o superior**
  - Descargar: https://www.java.com/es/download/

### Para compilar desde código fuente:
- **Java Development Kit (JDK) 11 o superior**
  - Descargar: https://www.oracle.com/java/technologies/downloads/
- **Maven 3.6 o superior**
  - Descargar: https://maven.apache.org/download.cgi

## 🚀 Instalación y Ejecución

### Opción 1: Ejecutar el archivo precompilado (Recomendado)

```bash
# Windows PowerShell
.\run.ps1

# macOS / Linux
./run.sh

# O directamente con Java
java -jar target/TimerCircle-1.0.0.jar
```

### Opción 2: Compilar desde código fuente

```bash
# Windows PowerShell
.\build.ps1

# macOS / Linux
./build.sh

# O manualmente con Maven
mvn clean package
```

Después de compilar, ejecuta la aplicación:

```bash
java -jar target/TimerCircle-1.0.0.jar
```

## 💡 Uso

1. **Introduce los minutos** en el campo de texto (por defecto 25 minutos)
2. **Haz clic en "Iniciar"** para comenzar el temporizador
3. **Observa el círculo** que se llena indicando el tiempo transcurrido
4. **Pausa/Reanuda** con el botón "Pausar"
5. **Reinicia** el temporizador con el botón "Reiniciar"
6. **Sonará una alerta** cuando termine el tiempo

### Opciones:
- ☑️ **Siempre encima** - Mantiene la ventana visible sobre otras aplicaciones

## 📦 Distribución de la Aplicación

El archivo compilado `TimerCircle-1.0.0.jar` es portable y puede ser:
- Compartido entre usuarios
- Incluido en un instalador
- Ejecutado en cualquier ordenador con Java 11+

## 🛠️ Estructura del Proyecto

```
Temporizador/
├── TimerCircle.java          # Código fuente de la aplicación
├── pom.xml                    # Configuración Maven
├── build.ps1                  # Script de compilación (Windows)
├── build.sh                   # Script de compilación (macOS/Linux)
├── run.ps1                    # Script de ejecución (Windows)
├── run.sh                     # Script de ejecución (macOS/Linux)
├── README.md                  # Este archivo
└── target/                    # Carpeta generada con los compilados
    └── TimerCircle-1.0.0.jar # JAR ejecutable
```

## 🔧 Personalización

Para modificar el proyecto:

1. Edita `TimerCircle.java` según necesites
2. Recompila con: `mvn clean package`
3. Distribuye el nuevo JAR

### Cambios comunes:
- **Título de la ventana**: Línea `setTitle("Temporizador de concentración")`
- **Tiempo por defecto**: Línea `inputSeconds.setText("25")`
- **Tamaño de ventana**: Línea `setMinimumSize(new Dimension(300, 320))`

## 📄 Licencia

Este proyecto es de código abierto.

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama para tu feature
3. Realiza commit de tus cambios
4. Push a la rama
5. Abre un Pull Request
