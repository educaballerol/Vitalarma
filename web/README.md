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

| Herramienta  | Versión |
| ------------ | ------- |
| Angular      | 22.1    |
| TypeScript   | 6.0     |
| Node.js      | 24 LTS  |
| Estilos      | SCSS    |

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

## Estructura

```
src/
├── styles/_tokens.scss        # tokens del design system (colores, tipografía, retícula)
├── styles.scss                # reset y estilos globales
└── app/
    ├── app.routes.ts          # una ruta por pantalla, con su código W-xx
    ├── models/                # tipos + datos de ejemplo
    ├── shared/                # componentes usados por varias pantallas
    │   └── top-bar/           # BarraSuperior (va en todas las pantallas)
    └── features/
        ├── panel/             # W-04
        └── area/              # W-05
```

Al agregar una pantalla: un componente en `features/<pantalla>/`, su
ruta en `app.routes.ts` con el código W-xx en el comentario, y **un
commit por pantalla**.

## Pantallas implementadas

| Código | Pantalla                      | Requerimiento          | Estado |
| ------ | ----------------------------- | ---------------------- | ------ |
| W-04   | Panel de tiempo por área      | W2 · Estadísticas      | ✅     |
| W-05   | Detalle de un área e histórico | W2 · Estadísticas      | ✅     |
| W-06   | Exportar reporte (modal)      | W6 · Reportes PDF      | ⬜     |
| W-07   | Tabla de todas las alarmas    | W3 · Gestión escritorio | ⬜     |
| W-08   | Edición masiva                | W3 · Gestión escritorio | ⬜     |
| W-09   | Crear alarma desde escritorio | W3 · Gestión escritorio | ⬜     |
| W-10   | Rutinas y bloques             | W7 · Rutinas           | ⬜     |
| W-11   | Editor de rutina y horarios   | W7 · Rutinas           | ⬜     |

Las pantallas de login (W-01 a W-03) quedan fuera a propósito: el
entregable es solo el flujo principal.

## Design system

Los tokens de `src/styles/_tokens.scss` salen del Figma
(*Design System - Web*, nodo `2105:3858`) y son los mismos que el
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
