package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * "FilaAjuste" de Figma (nodo 2117:496): fila de navegación con título +
 * subtítulo opcional + chevron. Fondo gris/10, borde inferior sutil.
 * Altura mínima 56dp por objetivo táctil (documentado en Figma).
 */
@Composable
fun SettingsRow(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = Dimens.SettingsRowMinHeight)
            .background(VitalarmaColors.fondoCapa)
            .border(Dimens.BorderThin, VitalarmaColors.bordeSutil) // aproximación: Figma solo pinta el borde inferior
            .clickable { onClick() }
            .padding(horizontal = Dimens.SpacingBase, vertical = Dimens.SpacingMd),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = VitalarmaType.body, color = VitalarmaColors.textoPrimario)
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = VitalarmaType.label,
                    color = VitalarmaColors.textoSecundario,
                    modifier = Modifier.padding(top = Dimens.Spacing2xs)
                )
            }
        }
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = VitalarmaColors.textoSecundario,
            modifier = Modifier.size(Dimens.IconSize)
        )
    }
}
