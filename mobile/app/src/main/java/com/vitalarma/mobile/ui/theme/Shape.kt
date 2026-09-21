package com.vitalarma.mobile.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Confirmado por las descripciones de componente en Figma:
 * "Esquinas rectas (IBM Carbon)" en Botón, Chip, Campo, FilaAjuste, TarjetaAlarma.
 * Excepciones EXPLÍCITAS documentadas en Figma: Interruptor (pastilla,
 * "convención de plataforma") y CírculoDía / Radio (círculo, "convención
 * de selector").
 */
object VitalarmaShapes {
    val None = RoundedCornerShape(0.dp)
    val Pill = RoundedCornerShape(50)   // Interruptor
    val Circle = CircleShape            // CírculoDía, Radio, Avatar
    val SheetTop = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) // hoja del bottom sheet
}

val VitalarmaMaterialShapes = Shapes(
    extraSmall = VitalarmaShapes.None,
    small = VitalarmaShapes.None,
    medium = VitalarmaShapes.None,
    large = VitalarmaShapes.None,
    extraLarge = VitalarmaShapes.None
)
