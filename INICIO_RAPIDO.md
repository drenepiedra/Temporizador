# INICIO RÁPIDO - Temporizador de Concentración

## ⚡ Para usuarios sin experiencia técnica

### Paso 1: Instalar Java (si no lo tienes)

1. Visita: https://www.java.com/es/download/
2. Descarga "Java Runtime Environment"
3. Ejecuta el instalador y sigue los pasos
4. Reinicia tu ordenador

### Paso 2: Ejecutar la aplicación

- **Windows:** Haz doble clic en `run.bat`
- **macOS/Linux:** Abre terminal y escribe: `./run.sh`
- **O cualquier SO:** `java -jar target/TimerCircle-1.0.0.jar`

---

## 👨‍💻 Para desarrolladores

### Compilar el proyecto:

```bash
# Windows
.\build.bat

# macOS/Linux
./build.sh

# O con Maven directamente
mvn clean package
```

### Ejecutar después de compilar:

```bash
# Windows
.\run.bat

# macOS/Linux
./run.sh

# O directamente
java -jar target/TimerCircle-1.0.0.jar
```

### Editar el código:

1. Abre `TimerCircle.java` en tu editor favorito
2. Haz los cambios que desees
3. Recompila con los comandos anteriores
4. Distribuye el nuevo JAR

---

## ✅ Verificar que todo funciona

Abre una terminal (PowerShell en Windows) y escribe:

```bash
java -version
```

Deberías ver algo como:

```
java version "11.0.x" o superior
```

Si ves un error, instala Java desde https://www.java.com/es/download/

---

## 📚 Más información

- Lee `README.md` para documentación completa
- Lee `DISTRIBUCION.md` para compartir con otros
- Consulta `pom.xml` para detalles de compilación

## 🎯 Uso de la aplicación

1. Introduce **minutos** (ej: 25)
2. Haz clic en **"Iniciar"**
3. El círculo se irá llenando con el tiempo transcurrido
4. Sonará una alerta cuando termine
5. Usa **"Pausar"** y **"Reiniciar"** según necesites
6. Marca **"Siempre encima"** para que la ventana flote sobre otras

¡Eso es todo! 🎉
