# Vitalarma

**Vitalarma** es una app de gestión de alarmas y recordatorios pensada
para estudiantes de posgrado que buscan equilibrar trabajo, estudio y
vida personal. Cuatro tipos de alarma (crítica, cauta, compartida,
recordatorio), clasificadas por área de vida, con un dashboard de
estadísticas de tiempo.

Proyecto individual del curso **UX Design** — Maestría en Ingeniería de
Software (MISO), desarrollado en pareja (binomio).

> Este repositorio es solo maquetación de interfaz (frontend), sin
> backend ni lógica de negocio real, tal como lo exigen las condiciones
> del proyecto del curso.

## Estructura del repositorio

```
Vitalarma/
├── web/       # Frontend web (maquetación navegable)
└── mobile/    # Frontend mobile — Android / Jetpack Compose
```

Cada carpeta tiene su propio README con detalles técnicos, guía de
instalación y estado de las pantallas implementadas:

- [`web/README.md`](./web/README.md)
- [`mobile/README.md`](./mobile/README.md)

## Diseño

El sistema de diseño está basado en **IBM Carbon** personalizado: paleta
cálida ámbar/naranja/café, tipografía IBM Plex Sans (texto general) +
IBM Plex Mono (datos numéricos), esquinas rectas con excepciones
documentadas (interruptores en forma de pastilla, selectores circulares
de día/tipo). El archivo de diseño (Figma) contiene los wireframes, el
Design System y los prototipos interactivos de ambas plataformas.
