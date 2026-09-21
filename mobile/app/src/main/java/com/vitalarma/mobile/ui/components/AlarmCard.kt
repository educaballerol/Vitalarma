package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.model.AlarmType
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * "TarjetaAlarma" de Figma (nodo 2114:517). Franja de 4px a la izquierda
 * con el color exacto del tipo; la palabra del tipo SIEMPRE acompaña al
 * color (nunca es el único portador de significado). Inactiva apaga el
 * switch y baja todo el texto a gris.
 */
@Composable
fun AlarmCard(
    time: String,
    type: AlarmType,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (barColor, typeTextColor) = colorsForType(type, checked)
    val primaryTextColor = if (checked) VitalarmaColors.textoPrimario else VitalarmaColors.textoTerciario
    val secondaryTextColor = if (checked) VitalarmaColors.textoSecundario else VitalarmaColors.textoTerciario

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(Dimens.BorderThin, VitalarmaColors.bordeSutil) // top+right+bottom (aproximación: los 4 lados)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .width(Dimens.AlarmCardLeftBarWidth)
                .fillMaxHeight()
                .background(barColor)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.SpacingBase),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row {
                    Text(text = time, style = VitalarmaType.dato, color = primaryTextColor)
                    Text(text = " · ", style = VitalarmaType.dato, color = VitalarmaColors.textoTerciario)
                    Text(text = type.label, style = VitalarmaType.label, color = typeTextColor)
                }
                Text(
                    text = description,
                    style = VitalarmaType.bodySmall,
                    color = secondaryTextColor,
                    modifier = Modifier.padding(top = Dimens.Spacing2xs)
                )
            }
            VitalarmaSwitch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
private fun colorsForType(type: AlarmType, active: Boolean): Pair<androidx.compose.ui.graphics.Color, androidx.compose.ui.graphics.Color> {
    if (!active) return VitalarmaColors.bordeFuerte to VitalarmaColors.textoTerciario
    return when (type) {
        AlarmType.CRITICA -> VitalarmaColors.tipoCritica to VitalarmaColors.tipoCriticaTexto
        AlarmType.CAUTA -> VitalarmaColors.tipoCauta to VitalarmaColors.tipoCautaTexto
        AlarmType.RECORDATORIO -> VitalarmaColors.tipoRecordatorio to VitalarmaColors.tipoRecordatorioTexto
        AlarmType.COMPARTIDA -> VitalarmaColors.tipoCompartida to VitalarmaColors.tipoCompartidaTexto
    }
}