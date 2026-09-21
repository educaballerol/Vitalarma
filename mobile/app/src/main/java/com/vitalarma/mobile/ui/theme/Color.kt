package com.vitalarma.mobile.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Tokens de color EXACTOS tomados de Figma (Get Design Context sobre M-04,
 * M-05, M-06, M-06b — archivo "Wireframes · Vitalarma", nodo de paleta
 * 2105-4130). Los nombres siguen la convención de variable de Figma en
 * español, traducida a camelCase, para que sea trivial cruzar contra el
 * panel de Figma si algo no cuadra.
 *
 * TODO: si al revisar más pantallas aparecen tokens nuevos (ej. un color
 * de éxito verde en "Alarma cumplida"), agrégalos aquí — nunca como un
 * Color(...) suelto dentro de un composable.
 */
object VitalarmaColors {

    // Fondos
    val fondoPagina = Color(0xFFFFFFFF)
    val fondoCapa = Color(0xFFF4F4F4)
    val fondoCapaAlta = Color(0xFFE0E0E0)
    val fondoAccion = Color(0xFFF2954A)       // naranja primario
    val fondoAccionSuave = Color(0xFFFFF3E6)  // naranja/10, tarjeta tipo seleccionada
    val fondoPeligro = Color(0xFFDA1E28)
    val fondoSuperposicion = Color(0xFF161616) // velo del bottom sheet (aplicar alpha ~0.5 al usar)

    // Texto
    val textoPrimario = Color(0xFF161616)
    val textoSecundario = Color(0xFF525252)
    val textoTerciario = Color(0xFF8D8D8D)
    val textoSobreAccion = Color(0xFF161616)  // texto oscuro sobre botón naranja
    val textoSobrePeligro = Color(0xFFFFFFFF)
    val textoAccion = Color(0xFFB3611D)       // texto de botón secundario / tab activo

    // Bordes
    val bordeSutil = Color(0xFFE0E0E0)
    val bordeFuerte = Color(0xFFC6C6C6)
    val bordeInteractivo = Color(0xFFF2954A)

    // Estado
    val estadoError = Color(0xFFDA1E28)

    // Tipo de alarma (color + texto por tipo — el color nunca es el único
    // portador de significado, la palabra del tipo siempre acompaña)
    val tipoCritica = Color(0xFFDA1E28)
    val tipoCriticaTexto = Color(0xFFDA1E28)
    val tipoCauta = Color(0xFFF1C21B)
    val tipoCautaTexto = Color(0xFF8E6A00)
    val tipoRecordatorio = Color(0xFFF2954A)
    val tipoRecordatorioTexto = Color(0xFFB3611D)
    val tipoCompartida = Color(0xFF0043CE)
    val tipoCompartidaTexto = Color(0xFF0043CE)
}
