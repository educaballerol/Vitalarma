package com.vitalarma.mobile.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vitalarma.mobile.R

// IBM Plex Sans / IBM Plex Mono como fuente empaquetada (res/font/), no
// descargable. Requiere que estos archivos existan en
// app/src/main/res/font/ (bajados de fonts.google.com):
//
//   ibm_plex_sans_regular.ttf
//   ibm_plex_sans_medium.ttf
//   ibm_plex_sans_semibold.ttf
//   ibm_plex_mono_regular.ttf
//   ibm_plex_mono_medium.ttf
//
// Si un nombre de archivo no coincide EXACTO con el de abajo (todo
// minusculas, guion bajo, sin espacios), Android Studio marcara
// unresolved reference en el R.font.xxx correspondiente.
//
// TODO: si todavia no tienes los .ttf copiados, cambia temporalmente
// FontFamily.Default por estas lineas (fuente del sistema, cero
// dependencias) y vuelve a esto cuando tengas los archivos:
//   val IBMPlexSans = FontFamily.SansSerif
//   val IBMPlexMono = FontFamily.Monospace

val IBMPlexSans = FontFamily(
    Font(R.font.ibm_plex_sans_regular, FontWeight.Normal),
    Font(R.font.ibm_plex_sans_medium, FontWeight.Medium),
    Font(R.font.ibm_plex_sans_semibold, FontWeight.SemiBold)
)

val IBMPlexMono = FontFamily(
    Font(R.font.ibm_plex_mono_regular, FontWeight.Normal),
    Font(R.font.ibm_plex_mono_medium, FontWeight.Medium)
)

// Escala de texto "Movil" tal como aparece en Figma. Usala siempre por
// nombre semantico (VitalarmaType.h1, VitalarmaType.boton, etc.) nunca
// declares un TextStyle suelto dentro de una pantalla.
object VitalarmaType {
    val h1 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.4).sp
    )
    val h3 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )
    val bodyLarge = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
    val body = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    )
    val bodyStrong = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 22.sp
    )
    val bodySmall = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp
    )
    val label = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.6.sp
    )
    val boton = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    )
    val caption = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
    val dato = TextStyle(
        fontFamily = IBMPlexMono,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 22.sp
    )
    val datoPequeno = TextStyle(
        fontFamily = IBMPlexMono,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp
    )
}
