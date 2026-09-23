package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

@Composable
fun VitalarmaCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val fondo = when {
        checked && enabled -> VitalarmaColors.fondoAccion
        checked -> VitalarmaColors.fondoCapaAlta
        else -> VitalarmaColors.fondoPagina
    }
    val borde = when {
        checked && enabled -> VitalarmaColors.bordeInteractivo
        enabled -> VitalarmaColors.textoPrimario
        else -> VitalarmaColors.bordeFuerte
    }

    Box(
        modifier = modifier
            .size(Dimens.CheckboxSize)
            .background(fondo, VitalarmaShapes.None)
            .border(Dimens.BorderThick, borde, VitalarmaShapes.None),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (enabled) VitalarmaColors.textoSobreAccion else VitalarmaColors.textoTerciario,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun CheckboxRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
            .padding(vertical = Dimens.SpacingSm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
    ) {
        VitalarmaCheckbox(checked = checked, enabled = enabled)

        Text(
            text = label,
            style = VitalarmaType.body,
            color = if (enabled) VitalarmaColors.textoPrimario else VitalarmaColors.textoTerciario
        )
    }
}
