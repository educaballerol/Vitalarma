package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.navigation.Routes
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * "BarraNav" de Figma (nodo 2116:538): 412×72, 3 pestañas. La activa se
 * marca por triple redundancia: franja naranja de 3px arriba + icono
 * teñido + etiqueta en naranja/70 — nunca solo color.
 */
@Composable
fun VitalarmaBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.BottomNavHeight)
            .background(VitalarmaColors.fondoPagina)
            .border(Dimens.BorderThin, VitalarmaColors.bordeSutil)
    ) {
        NavItem(
            label = "Alarmas",
            icon = Icons.Filled.Alarm,
            selected = currentRoute == Routes.ALARM_LIST,
            onClick = { onNavigate(Routes.ALARM_LIST) },
            modifier = Modifier.weight(1f)
        )
        NavItem(
            label = "Rutinas",
            icon = Icons.Filled.Repeat,
            selected = currentRoute == Routes.ROUTINE_LIST,
            onClick = { onNavigate(Routes.ROUTINE_LIST) },
            modifier = Modifier.weight(1f)
        )
        NavItem(
            label = "Ajustes",
            icon = Icons.Filled.Settings,
            selected = currentRoute == Routes.SETTINGS,
            onClick = { onNavigate(Routes.SETTINGS) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun NavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tint = if (selected) VitalarmaColors.fondoAccion else VitalarmaColors.textoSecundario
    val labelColor = if (selected) VitalarmaColors.textoAccion else VitalarmaColors.textoSecundario

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
            .padding(bottom = Dimens.SpacingSm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.layout.Box(
            Modifier
                .width(Dimens.BottomNavAccentWidth)
                .height(Dimens.BottomNavAccentHeight)
                .background(if (selected) VitalarmaColors.fondoAccion else androidx.compose.ui.graphics.Color.Transparent)
        )
        androidx.compose.foundation.layout.Spacer(Modifier.weight(1f))
        Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(Dimens.IconSize))
        Text(text = label, style = VitalarmaType.caption, color = labelColor)
        androidx.compose.foundation.layout.Spacer(Modifier.weight(1f))
    }
}
