package com.vitalarma.mobile.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Todos los valores de este archivo vienen confirmados por Get Design
 * Context sobre M-04, M-05, M-06 y M-06b — no son estimaciones. Si una
 * pantalla nueva usa un espaciado que no está aquí, agrégalo con su
 * nombre semántico (no un dp suelto en el composable).
 */
object Dimens {

    // Grid de espaciado (tokens "espacio-*" de Figma)
    val Spacing2xs = 2.dp
    val SpacingXs = 4.dp
    val SpacingSm = 8.dp
    val SpacingMd = 12.dp
    val SpacingBase = 16.dp
    val SpacingXl = 24.dp
    val Spacing2xl = 32.dp

    // Márgenes de pantalla
    val ScreenHorizontalMargin = 24.dp // "margen-lateral"

    // Componentes
    val ButtonHeight = 48.dp           // "alto-boton"
    val TextFieldHeight = 48.dp        // caja del "Campo"
    val IconSize = 24.dp

    // Chips
    val ChipFilterHeight = 40.dp       // Chip de filtro (Todas/Críticas/...)
    val DayChipSize = 42.dp            // CírculoDía
    val DayChipGap = 6.dp              // separación entre CírculoDía (42×42, gap 6)

    // TarjetaAlarma
    val AlarmCardLeftBarWidth = 4.dp

    // FilaAjuste
    val SettingsRowMinHeight = 56.dp

    // BarraNav / BarraEstado
    val BottomNavHeight = 72.dp
    val BottomNavAccentHeight = 3.dp
    val BottomNavAccentWidth = 40.dp
    val StatusBarHeight = 32.dp

    // Switch (Interruptor) — tamaño real de Figma, distinto del Switch
    // default de Material3 (que es más angosto); ver VitalarmaSwitch.kt
    val SwitchWidth = 48.dp
    val SwitchHeight = 28.dp

    // Checkbox / Radio (Casilla / Radio)
    val CheckboxSize = 20.dp
    val RadioSize = 20.dp

    // Bordes
    val BorderThin = 1.dp
    val BorderThick = 2.dp             // borde de TarjetaTipo cuando está Seleccionada

    // Bottom sheet
    val SheetHandleWidth = 40.dp
    val SheetHandleHeight = 4.dp
}
