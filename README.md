# MP-Videogame-Group6

# 🧛‍♂️ PELEAS EN ULLEWAND 🐺

Videojuego de combate por turnos entre criaturas fantásticas, desarrollado en Java como proyecto de la asignatura **Metodología de la Programación** (URJC, curso 2025/2026).

---

## 📋 Requisitos del sistema

- **Java JDK 21** o superior ([descargar](https://adoptium.net/))
- Terminal compatible con **ANSI** (colores y códigos de escape)
- Sistema operativo: Linux, macOS o Windows (con soporte para scripts bash o comandos equivalentes)
- **Git** (opcional, para clonar el repositorio)

---

## 🚀 Instalación y compilación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio
```

## Estructura de proyecto

### MP-Videogame-Group6/
- ├── config/               ← archivos de configuración (.txt, .dat)
- ├── src/
- │   ├── Main.java         ← punto de entrada
- │   ├── Game/             ← paquete principal
- │   └── DB/               ← persistencia
- └── README.md

## Comando de ejecución

```bash
javac -d out -cp . $(find src -name "*.java" -not -path "*/Test/*")
```
