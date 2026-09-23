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
├── MainActivity.kt           # punto de entrada, arranca VitalarmaNavHost
├── navigation/               # rutas y grafo de navegación (Navigation Compose)
├── models/                   # data classes y listas de ejemplo (sin backend)
├── ui/
│   ├── theme/                # paleta, tipografía, espaciados y formas (design system)
│   ├── components/           # componentes reutilizables (botón, campo, chips, etc.)
│   └── screens/              # pantallas del flujo de alarmas, rutinas y ajustes
└── res/font/                 # IBM Plex Sans / IBM Plex Mono empaquetadas
```

## Pantallas implementadas

Numeración verificada directo del archivo de Figma ("Mockup · Móvil",
que agrupa M-01 a M-22 con autor de diseño asignado a cada frame).
Quedan fuera de esta tabla a propósito: las pantallas de login (M-01,
M-02, M-03, M-03b) y M-21/M-22 (Contactos de confianza, Cuenta y
perfil) — estas últimas no por ser login, sino porque no forman parte
del flujo principal del proyecto (creación y gestión de alarmas y
rutinas).

| Código | Pantalla                    | Autor     | Estado       |
| ------ | --------------------------- | --------- | ------------ |
| M-04   | Mis alarmas                 | Eduardo   | Implementada |
| M-05   | Editar alarma               | Eduardo   | Implementada |
| M-06   | Eliminar alarma             | Eduardo   | Implementada |
| M-06b  | Escoger tipo (bottom sheet) | Eduardo   | Implementada |
| M-07   | Definir hora                | Humberto  | Implementada |
| M-08   | Qué tipo de alarma          | Humberto  | Implementada |
| M-09   | Detalles                    | Eduardo¹  | Implementada |
| M-10   | Alarma creada               | Eduardo¹  | Implementada |
| M-11   | Alarma sonando              | Humberto  | Implementada |
| M-12   | Escanear el objeto          | Humberto  | Implementada |
| M-13   | Alarma cumplida             | Humberto¹ | Implementada |
| M-14   | Respaldo activado           | Humberto¹ | Implementada |
| M-15   | Rutinas                     | Humberto  | Implementada |
| M-16   | Detalle de rutina           | Eduardo   | Implementada |
| M-17   | Nueva rutina                | Eduardo   | Implementada |
| M-18   | Eliminar rutina             | Humberto  | Implementada |
| M-19   | Ajustes                     | Humberto  | Implementada |
| M-20   | Dispositivos de respaldo    | Humberto  | Implementada |

¹ El diseño en Figma asigna estas pantallas al otro integrante del
binomio, pero el código lo escribió quien aparece aquí — la tabla
refleja autoría de código, no de diseño.

El flujo principal del proyecto es la **creación y gestión de alarmas y
rutinas**: desde armar una alarma nueva (hora, tipo, detalles),
incluimos su ejecución (sonando, escaneo del objeto, respaldo si no se
reacciona), hasta agruparlas en rutinas y ajustar cómo se comportan
(modo cauto, dispositivos de respaldo). Las pantallas de la tabla cubren
ese flujo de punta a punta entre los dos integrantes del equipo.

## Cómo probar el flujo principal

1. **Crear alarma:** `+ Crear alarma` → Definir hora → Escoger tipo →
   Detalles (el campo "Título", la nota de voz y el vínculo con
   calendario son opcionales; "Cómo se apaga" solo aparece si elegiste
   tipo Crítica) → Crear alarma.
2. **Alarma crítica cumplida:** `Probar alarma crítica` → Apagar
   escaneando → tocar el visor → Alarma cumplida.
3. **Alarma crítica ignorada:** `Probar alarma crítica` y no tocar
   nada. A los 15 segundos aparece sola la pantalla de respaldo. El
   tiempo está en `SEGUNDOS_HASTA_RESPALDO`, en `AlarmRingingScreen.kt`.
4. **Rutinas:** pestaña "Rutinas" de la barra inferior. `+ Nueva
rutina` abre M-17 (nombre, días activos, agregar alarmas). Tocar
   una rutina existente abre M-16 (Detalle de rutina): puedes cambiar
   los días activos y prender/apagar cada alarma de la rutina.
   "Eliminar rutina" (M-18) se abre desde dos caminos: manteniendo
   pulsada una rutina en la lista (M-15), o tocando "Eliminar rutina"
   dentro de su detalle (M-16).
5. **Ajustes:** pestaña "Ajustes" de la barra inferior (M-19: modo
   cauto, horario silencioso). Desde ahí, "Dispositivos de respaldo"
   abre M-20. Contactos de confianza y Cuenta quedan fuera del alcance
   de esta entrega (ver nota en "Pantallas implementadas").

## Design system

El sistema de diseño está basado en **IBM Carbon** personalizado: esquinas
rectas en la mayoría de componentes (excepciones documentadas: el
interruptor en forma de pastilla y los selectores circulares de día/tipo),
paleta cálida ámbar/naranja/café, y tipografía IBM Plex Sans (texto
general) + IBM Plex Mono (datos numéricos, como la hora). Los tokens
exactos (colores hex, tamaños, espaciados) están documentados con
comentarios en `ui/theme/Color.kt`, `Type.kt` y `Dimens.kt`, extraídos
directamente del archivo de Figma del proyecto.
