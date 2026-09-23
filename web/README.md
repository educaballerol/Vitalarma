# Vitalarma — Web

Maquetación en código (frontend web, no funcional) de **Vitalarma**.
Proyecto del curso de UX Design — Maestría en Ingeniería de Software
(MISO).

> Este módulo es solo maquetación de interfaz: no hay backend, ni
> persistencia real, ni lógica de negocio. Los datos que se ven (horas
> por área, alarmas fallidas) son datos de ejemplo tomados del Figma y
> viven en `src/app/models/`, tal como lo permiten las condiciones del
> proyecto.

## Frameworks y versiones

| Herramienta | Versión |
| ----------- | ------- |
| Angular     | 22.1    |
| TypeScript  | 6.0     |
| Node.js     | 24 LTS  |
| Estilos     | SCSS    |

Componentes **standalone** con la sintaxis de control de flujo nueva
(`@for`, `@if`) y `signal()` / `computed()` para el estado. Sin
librerías de UI ni de gráficas: todo se construye con el design system
propio.

## Requisitos e instalación

1. Instalar **Node.js 20 o superior** (probado con 24 LTS).
2. Desde la carpeta `web/`:

```bash
npm install     # instala dependencias (no están en el repo)
npm start       # servidor de desarrollo en http://localhost:4200
npm run build   # compilado de producción en dist/
```

`node_modules/` y `dist/` están en `.gitignore`: no se suben. Lo que sí
se sube es `package-lock.json`, que es lo que garantiza que a todos nos
instale las mismas versiones.

## Tipografía

IBM Plex Sans (texto de interfaz) + IBM Plex Mono (datos numéricos),
servidas desde Google Fonts (`<link>` en `src/index.html`) en vez de
archivos locales. Si el entorno de evaluación no tiene internet, el
texto cae al stack de respaldo definido en `--fuente-sans`/`--fuente-mono`
de `_tokens.scss`, no se rompe, pero no se ve IBM Plex.

## Estructura

```
src/
├── styles/_tokens.scss        # tokens del design system (colores, tipografía, retícula)
├── styles/_controles.scss     # casilla y radio, compartidos por todas las pantallas
├── styles.scss                # reset y estilos globales
└── app/
    ├── app.routes.ts          # una ruta por pantalla, con su código W-xx
    ├── models/                # tipos + datos de ejemplo
    │   ├── alarma.model.ts
    │   └── rutina.model.ts    # compartido entre W-10 y W-11
    ├── shared/                # componentes usados por varias pantallas
    │   ├── top-bar/           # BarraSuperior (va en todas las pantallas)
    │   ├── dialogo-exportar/  # W-06, modal que abre desde W-05
    │   └── acciones-bloque/   # W-08, barra de edición masiva (usada por alarmas/)
    └── features/
        ├── panel/             # W-04
        ├── area/              # W-05
        ├── alarmas/           # W-07
        ├── nueva-alarma/      # W-09
        ├── rutinas/           # W-10
        └── editor-rutina/     # W-11
```

Al agregar una pantalla: un componente en `features/<pantalla>/`, su
ruta en `app.routes.ts` con el código W-xx en el comentario, y **un
commit por pantalla**.

## Pantallas implementadas

| Código | Pantalla                       | Autor    | Requerimiento           | Estado |
| ------ | ------------------------------ | -------- | ----------------------- | ------ |
| W-04   | Panel de tiempo por área       | Humberto | W2 · Estadísticas       | ✅     |
| W-05   | Detalle de un área e histórico | Humberto | W2 · Estadísticas       | ✅     |
| W-06   | Exportar reporte (modal)       | Humberto | W6 · Reportes PDF       | ✅     |
| W-07   | Tabla de todas las alarmas     | Humberto | W3 · Gestión escritorio | ✅     |
| W-08   | Edición masiva (componente)    | Eduardo  | W3 · Gestión escritorio | ✅     |
| W-09   | Crear alarma desde escritorio  | Eduardo  | W3 · Gestión escritorio | ✅     |
| W-10   | Rutinas y bloques              | Eduardo  | W7 · Rutinas            | ✅     |
| W-11   | Editor de rutina y horarios    | Eduardo  | W7 · Rutinas            | ✅     |

Las pantallas de login (W-01 a W-03) quedan fuera a propósito: el
entregable es solo el flujo principal.

## Design system

Los tokens de `src/styles/_tokens.scss` salen del Figma
(_Design System - Web_, nodo `2105:3858`) y son los mismos que el
módulo mobile tiene en `ui/theme/Color.kt` y `Type.kt`. **Nunca
escribas un hex suelto en un componente**: si falta un token, agrégalo
al archivo con su nombre semántico.

- Paleta: 10 grises estilo Carbon + 9 tonos de naranja a café, con
  `#F2954A` como primario de acción.
- Colores semánticos (éxito, advertencia, error, info): reservados para
  estados del sistema. En Vitalarma además ya significan tipo de alarma,
  así que **no se usan como decoración ni como color de una serie**.
- Tipografía: IBM Plex Sans (texto) + IBM Plex Mono (datos numéricos),
  escala de razón √φ = 1,272 → 10 · 13 · 16 · 20 · 26 · 33 · 42.
- Esquinas rectas (IBM Carbon), salvo el interruptor (pastilla) y el
  radio (círculo).

### Nota abierta: la paleta de las cuatro áreas de vida

El design system no tiene una paleta categórica de cuatro tonos, y los
semánticos están reservados. Las cuatro áreas usan por ahora una rampa
cálida de cuatro pasos (`naranja-90 / 70 / 50 / 30`), que es la de mejor
separación de las que se probaron, pero el par de tonos claros queda por
debajo del umbral en el que dos colores se distinguen con comodidad.

Por eso en W-04 el color **nunca viaja solo**: hay leyenda, cada barra
lleva su total escrito, los segmentos van separados por 2 px y existe
una vista de tabla con el detalle completo.

En W-05 el problema es distinto: ahí solo hay una serie por pantalla, así
que no hace falta separar cuatro colores, pero un trazo de 2 px en
`naranja-30` es ilegible sobre blanco (contraste 1,46:1). Por eso la
gráfica de línea usa los tokens `--area-*-fuerte`, pasos más oscuros de
la misma rampa que llegan a 3:1.

Queda pendiente decidir en Figma si se agrega una paleta categórica
propia para las áreas.

### Nota: W-08 no es una ruta, es un componente condicional en W-07

"Edición masiva" no tiene URL propia — es la barra negra fija al fondo
de la pantalla (`shared/acciones-bloque/`) que aparece dentro de
`Alarmas` cuando `totalSeleccionadas() > 0`. No cuenta como pantalla
para el límite de 3 por estudiante porque no es una ruta ni un frame
navegable independiente, es un estado de la tabla que ya existía. Los
tres botones de acción (Cambiar tipo, Mover a rutina, Eliminar) son
interactivos pero no ejecutan ninguna lógica real, consistente con el
resto del proyecto.

### Nota abierta: campo de hora nativo (W-09)

El campo "Hora" usa `<input type="time">` nativo, sin JS adicional
encima: el gesto de apertura es el nativo de cada navegador (clic en
el ícono, o escribir los dígitos directamente). El ícono se dejó tal
como lo renderiza cada motor — no se intentó ocultarlo ni
unificarlo, porque Firefox no expone un pseudo-elemento equivalente a
`::-webkit-calendar-picker-indicator` para eso. El `placeholder` del
mockup ("hh:mm a.m.") tampoco se replica: los navegadores ignoran ese
atributo en inputs de hora/fecha por especificación, y en su lugar
muestran su propio formato de referencia cuando el campo está vacío.
Ambas son limitaciones de plataforma, no diferencias de diseño
intencionales.

### Nota abierta: datos de ejemplo de W-10 no coinciden con otras pantallas

Las alarmas que se ven en W-10 (agrupadas por rutina) usan datos
locales propios de esta pantalla, que no necesariamente coinciden con
los datos de ejemplo que se ven en W-07 (tabla de alarmas) — esto es
intencional, no un error de datos compartidos rotos. El enunciado del
proyecto establece que la entrega es una maquetación **no funcional**,
sin backend ni persistencia real, así que cada pantalla usa datos de
ejemplo independientes según lo que necesite mostrar.

### Nota abierta: arrastrar y soltar en W-10 (solo mouse)

Reordenar alarmas entre rutinas usa el HTML5 Drag and Drop API nativo
del navegador, sin librerías. Funciona con mouse en cualquier
navegador de escritorio, pero **no responde a gestos táctiles** — en
un celular o tablet el ícono de arrastre no hace nada. Como esta es la
maqueta web (pensada para escritorio), no debería ser un problema para
la demo, pero para aclarar si el tutor la prueba desde un dispositivo
táctil, el reordenamiento simplemente no va a funcionar.

### Nota abierta: W-11 sirve tanto para crear como para editar

`/rutinas/nueva` y `/rutinas/:id/editar` cargan el mismo componente
(`EditorRutina`). Sin `id` en la ruta arranca vacío; con `id` busca la
rutina en `RUTINAS_RESUMEN` (`models/rutina.model.ts`) y precarga sus
datos. "Guardar rutina" no persiste nada, solo navega de vuelta a
`/rutinas`, consistente con que toda la entrega es no funcional.

El selector "+ Agregar una alarma existente" sí lee de datos reales:
tira de `ALARMAS` (`models/alarma.model.ts`), el mismo array que usa
la tabla de W-07, filtrando las que ya están en la rutina.
