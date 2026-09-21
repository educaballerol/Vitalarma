package com.vitalarma.mobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Reemplaza el Theme.kt que genera el wizard por este. Solo esquema claro:
 * el Figma no define variante oscura, así que no inventamos una — si el
 * profesor pide dark mode más adelante, se agrega aquí explícitamente.
 *
 * Los roles de MaterialTheme.colorScheme se usan SOLO como puente para que
 * componentes base de Compose (Switch, TextField, etc.) tengan un color
 * por defecto razonable. Para todo lo que sea texto o fondo semántico de
 * Vitalarma, los composables de ui/components/ referencian VitalarmaColors
 * directamente, no MaterialTheme.colorScheme — así evitamos forzar los 25
 * roles de Material3 a encajar en un sistema de tokens que es más simple
 * (Carbon) y no tiene ese mismo vocabulario.
 */
private val VitalarmaColorScheme = lightColorScheme(
    primary = VitalarmaColors.fondoAccion,
    onPrimary = VitalarmaColors.textoSobreAccion,
    secondary = VitalarmaColors.tipoCompartida,
    onSecondary = VitalarmaColors.textoSobrePeligro,
    tertiary = VitalarmaColors.tipoCauta,
    onTertiary = VitalarmaColors.tipoCautaTexto,
    error = VitalarmaColors.estadoError,
    onError = VitalarmaColors.textoSobrePeligro,
    background = VitalarmaColors.fondoPagina,
    onBackground = VitalarmaColors.textoPrimario,
    surface = VitalarmaColors.fondoPagina,
    onSurface = VitalarmaColors.textoPrimario,
    surfaceVariant = VitalarmaColors.fondoCapa,
    onSurfaceVariant = VitalarmaColors.textoSecundario,
    outline = VitalarmaColors.bordeFuerte,
    outlineVariant = VitalarmaColors.bordeSutil
)

private val VitalarmaTypography = Typography(
    headlineLarge = VitalarmaType.h1,
    titleMedium = VitalarmaType.h3,
    bodyLarge = VitalarmaType.bodyLarge,
    bodyMedium = VitalarmaType.body,
    bodySmall = VitalarmaType.bodySmall,
    labelSmall = VitalarmaType.label,
    labelLarge = VitalarmaType.boton,
    labelMedium = VitalarmaType.caption
)

@Composable
fun MobileTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VitalarmaColorScheme,
        shapes = VitalarmaMaterialShapes,
        typography = VitalarmaTypography,
        content = content
    )
}
