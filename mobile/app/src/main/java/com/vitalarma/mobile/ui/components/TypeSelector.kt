package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.model.AlarmType
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

private data class TypeInfo(val type: AlarmType, val title: String, val detail1: String, val detail2: String, val color: androidx.compose.ui.graphics.Color, val textColor: androidx.compose.ui.graphics.Color)

private val typeInfos = listOf(
    TypeInfo(AlarmType.CRITICA, "Crítica", "sirena ascendente", "No se apaga sin completar una acción", VitalarmaColors.tipoCritica, VitalarmaColors.tipoCriticaTexto),
    TypeInfo(AlarmType.CAUTA, "Cauta", "vibración corta", "Solo vibra en lugares públicos", VitalarmaColors.tipoCauta, VitalarmaColors.tipoCautaTexto),
    TypeInfo(AlarmType.COMPARTIDA, "Compartida", "timbre doble", "Avisa también a otra persona", VitalarmaColors.tipoCompartida, VitalarmaColors.tipoCompartidaTexto),
    TypeInfo(AlarmType.RECORDATORIO, "Recordatorio", "un toque", "Suena una vez, o mide un tiempo", VitalarmaColors.tipoRecordatorio, VitalarmaColors.tipoRecordatorioTexto)
)

/**
 * "SelectorTipo" de Figma (nodo 2165:590): grupo de selección EXCLUSIVA —
 * tocar una tarjeta cambia el grupo entero (comportamiento real de radio).
 * El estado se hoistea aquí arriba (selected/onSelectedChange), como pide
 * Figma: "una tarjeta suelta no puede apagar a sus hermanos".
 */
@Composable
fun TypeSelector(
    selected: AlarmType,
    onSelectedChange: (AlarmType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Dimens.SpacingMd)) {
        typeInfos.forEach { info ->
            TypeCard(
                info = info,
                isSelected = info.type == selected,
                onClick = { onSelectedChange(info.type) }
            )
        }
    }
}

@Composable
private fun TypeCard(info: TypeInfo, isSelected: Boolean, onClick: () -> Unit) {
    val bg = if (isSelected) VitalarmaColors.fondoAccionSuave else VitalarmaColors.fondoPagina
    val borderWidth = if (isSelected) Dimens.BorderThick else Dimens.BorderThin
    val detailColor = if (isSelected) info.textColor else VitalarmaColors.textoSecundario

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg, VitalarmaShapes.None)
            .border(borderWidth, info.color, VitalarmaShapes.None)
            .clickable { onClick() }
            .padding(Dimens.SpacingMd),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Dimens.Spacing2xs)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Dimens.SpacingMd)) {
            RadioDot(selected = isSelected, color = info.color)
            Text(text = info.title, style = VitalarmaType.bodyStrong, color = VitalarmaColors.textoPrimario)
        }
        Text(text = info.detail1, style = VitalarmaType.caption, color = detailColor)
        Text(text = info.detail2, style = VitalarmaType.caption, color = detailColor)
    }
}

/**
 * "Radio" de Figma (nodo 2111:463): 20×20, circular (excepción
 * documentada). Sin interacción propia — la resuelve el grupo padre.
 */
@Composable
private fun RadioDot(selected: Boolean, color: androidx.compose.ui.graphics.Color) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .size(Dimens.RadioSize)
            .background(if (selected) androidx.compose.ui.graphics.Color.Transparent else VitalarmaColors.fondoPagina, VitalarmaShapes.Circle)
            .border(if (selected) Dimens.BorderThick else Dimens.BorderThin, if (selected) color else VitalarmaColors.bordeFuerte, VitalarmaShapes.Circle),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            androidx.compose.foundation.layout.Box(
                Modifier.size(10.dp).background(color, VitalarmaShapes.Circle)
            )
        }
    }
}
