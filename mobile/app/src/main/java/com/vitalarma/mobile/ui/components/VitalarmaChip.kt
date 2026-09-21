package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * "CírculoDía" de Figma (nodo 2112:434): 42×42, circular (excepción
 * documentada). Activo = naranja + texto oscuro. Inactivo = fondo-capa
 * + borde sutil + texto secundario.
 */
@Composable
fun DayChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (selected) VitalarmaColors.fondoAccion else VitalarmaColors.fondoCapa
    val fg = if (selected) VitalarmaColors.textoSobreAccion else VitalarmaColors.textoSecundario

    Box(
        modifier = modifier
            .size(Dimens.DayChipSize)
            .background(bg, VitalarmaShapes.Circle)
            .then(
                if (!selected) Modifier.border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.Circle)
                else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, style = VitalarmaType.bodySmall, color = fg)
    }
}

/**
 * "Chip" de filtro de Figma (nodo 2112:427): rectangular, esquinas rectas
 * (NO uses FilterChip de Material3 — viene redondeado por defecto).
 * Seleccionado = relleno naranja + texto oscuro; Normal = fondo-capa +
 * borde sutil + texto secundario.
 */
@Composable
fun FilterChipItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (selected) VitalarmaColors.fondoAccion else VitalarmaColors.fondoCapa
    val border = if (selected) VitalarmaColors.bordeInteractivo else VitalarmaColors.bordeSutil
    val fg = if (selected) VitalarmaColors.textoSobreAccion else VitalarmaColors.textoSecundario

    Box(
        modifier = modifier
            .height(Dimens.ChipFilterHeight)
            .background(bg, VitalarmaShapes.None)
            .border(Dimens.BorderThin, border, VitalarmaShapes.None)
            .clickable { onClick() }
            .padding(horizontal = Dimens.SpacingBase),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, style = VitalarmaType.bodySmall, color = fg)
    }
}
