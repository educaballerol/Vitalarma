# Vitalarma — Mobile

Maquetación en código (frontend mobile, no funcional) de **Vitalarma**, una
app de gestión de alarmas y recordatorios pensada para estudiantes de
posgrado que buscan equilibrar trabajo, estudio y vida personal. Proyecto
del curso de UX Design — Maestría en Ingeniería de Software (MISO).

> Este módulo es solo maquetación de interfaz: no hay backend, ni
> persistencia real, ni lógica de negocio. Los datos que se ven (alarmas,
> horarios, contactos) son datos de ejemplo hardcodeados en
> `model/Alarm.kt` y `model/Contact.kt`, tal como lo permiten las
> condiciones del proyecto.

## Frameworks y versiones

| Herramienta                 | Versión                       |
| --------------------------- | ----------------------------- |
| Kotlin                      | 2.2.10                        |
| Android Gradle Plugin (AGP) | 9.4.1                         |
| Compose BOM                 | 2026.02.01                    |
| Navigation Compose          | 2.8.4                         |
| Material 3 (Compose)        | gestionado por el Compose BOM |
| Material Icons Extended     | gestionado por el Compose BOM |
| Core KTX                    | 1.19.0                        |
| Lifecycle Runtime KTX       | 2.11.0                        |
| Activity Compose            | 1.13.0                        |

Toda la UI está escrita en **Jetpack Compose** (declarativo, sin XML de
layouts). La gestión de dependencias usa el catálogo de versiones de
Gradle (`gradle/libs.versions.toml`), no versiones sueltas en
`build.gradle.kts`.

**Configuración del proyecto:**

- `applicationId` / paquete base: `com.vitalarma.mobile`
- `minSdk`: 27 (Android 8.1 Oreo) — requisito del curso
- `targetSdk` / `compileSdk`: 37
- Java/Kotlin target: 11

## Requisitos para compilar

- Android Studio (versión reciente con soporte para AGP 9.x — Koala o
  superior)
- JDK 11 o superior (normalmente ya viene con Android Studio)
- SDK Platform API 37 instalado (Android Studio lo pide automáticamente
  en el primer sync si falta)
- Conexión a internet en el primer build (Gradle descarga dependencias)

## Guía de instalación y ejecución

1. **Clonar el repositorio** y abrir la carpeta `mobile/` como proyecto
   en Android Studio (`File > Open`, seleccionar la carpeta `mobile/`,
   no la raíz del repo).
2. Esperar el **Gradle Sync** inicial (la barra de progreso abajo). Si
   pide instalar el SDK Platform 37, aceptar.
3. Conectar un dispositivo Android físico con **Depuración USB**
   activada (Ajustes > Opciones de desarrollador > Depuración USB), o
   crear un **emulador** (`Device Manager > Create Device`) con API 27
   o superior.
4. Seleccionar el dispositivo en el dropdown superior de Android
   Studio (junto al botón ▶) y hacer clic en **Run** (▶ verde).
5. La app se instala y abre sola con el nombre **Vitalarma**. Arranca
   directo en la pantalla "Mis Alarmas" (M-04).

**Alternativa por línea de comandos** (desde la carpeta `mobile/`, con
un dispositivo ya conectado y autorizado):

```bash
./gradlew installDebug
```

Esto compila el APK debug y lo instala directo en el dispositivo
conectado.

## Estructura del proyecto

```
app/src/main/java/com/vitalarma/mobile/
├── MainActivity.kt          # punto de entrada, arranca VitalarmaNavHost
├── navigation/               # rutas y grafo de navegación (Navigation Compose)
├── model/                    # data classes y listas de ejemplo (sin backend)
├── ui/
│   ├── theme/                 # paleta, tipografía, espaciados y formas (design system)
│   ├── components/            # componentes reutilizables (botón, campo, chips, etc.)
│   └── screens/alarms/        # pantallas del flujo de alarmas
└── res/font/                  # IBM Plex Sans / IBM Plex Mono empaquetadas
```

## Pantallas implementadas

| Mockup | Pantalla                      | Autor   | Estado                         |
| ------ | ----------------------------- | ------- | ------------------------------ |
| M-04   | Mis alarmas                   | Eduardo | ✅ Completa                    |
| M-05   | Editar alarma                 | Eduardo | ✅ Completa                    |
| M-06   | Eliminar alarma               | Eduardo | ✅ Completa                    |
| M-06b  | Escoge un tipo (bottom sheet) | Eduardo | ✅ Completa, integrada en M-05 |

<!-- TODO: agregar aquí las filas de las pantallas faltante -->

## Design system

El sistema de diseño está basado en **IBM Carbon** personalizado: esquinas
rectas en la mayoría de componentes (excepciones documentadas: el
interruptor en forma de pastilla y los selectores circulares de día/tipo),
paleta cálida ámbar/naranja/café, y tipografía IBM Plex Sans (texto
general) + IBM Plex Mono (datos numéricos, como la hora). Los tokens
exactos (colores hex, tamaños, espaciados) están documentados con
comentarios en `ui/theme/Color.kt`, `Type.kt` y `Dimens.kt`, extraídos
directamente del archivo de Figma del proyecto.
