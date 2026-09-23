package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

enum class Meridiem(val label: String) { AM("AM"), PM("PM") }

@Composable
fun TimeWheel(
    hour: Int,
    minute: Int,
    meridiem: Meridiem,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
    onMeridiemChange: (Meridiem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXl)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm),
            modifier = Modifier.clearAndSetSemantics {
                contentDescription =
                    "Hora seleccionada: ${formatoHora(hour)} y ${formatoMinuto(minute)} ${meridiem.label}"
            }
        ) {
            WheelColumn(
                value = hour,
                formato = ::formatoHora,
                onChange = { onHourChange(ciclar(it, 1, 12)) }
            )

            Text(
                text = ":",
                style = VitalarmaType.h1,
                color = VitalarmaColors.textoTerciario
            )

            WheelColumn(
                value = minute,
                formato = ::formatoMinuto,
                onChange = { onMinuteChange(ciclar(it, 0, 59)) }
            )
        }

        MeridiemSwitch(selected = meridiem, onSelectedChange = onMeridiemChange)
    }
}

@Composable
private fun WheelColumn(
    value: Int,
    formato: (Int) -> String,
    onChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
    ) {
        Text(
            text = formato(value - 1),
            style = VitalarmaType.bodyLarge,
            color = VitalarmaColors.textoTerciario,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(84.dp)
                .clickable { onChange(value - 1) }
                .padding(vertical = Dimens.SpacingXs)
        )

        Box(
            modifier = Modifier
                .width(84.dp)
                .background(VitalarmaColors.fondoAccionSuave, VitalarmaShapes.None)
                .border(Dimens.BorderThick, VitalarmaColors.bordeInteractivo, VitalarmaShapes.None)
                .padding(vertical = Dimens.SpacingSm),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = formato(value),
                style = VitalarmaType.h1,
                color = VitalarmaColors.textoPrimario,
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = formato(value + 1),
            style = VitalarmaType.bodyLarge,
            color = VitalarmaColors.textoTerciario,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(84.dp)
                .clickable { onChange(value + 1) }
                .padding(vertical = Dimens.SpacingXs)
        )
    }
}

@Composable
private fun MeridiemSwitch(
    selected: Meridiem,
    onSelectedChange: (Meridiem) -> Unit
) {
    Row(
        modifier = Modifier
            .width(120.dp)
            .height(Dimens.ButtonHeight)
            .border(Dimens.BorderThin, VitalarmaColors.bordeFuerte, VitalarmaShapes.None)
    ) {
        Meridiem.entries.forEach { opcion ->
            val activo = opcion == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.ButtonHeight)
                    .background(
                        if (activo) VitalarmaColors.fondoAccion else VitalarmaColors.fondoPagina,
                        VitalarmaShapes.None
                    )
                    .clickable { onSelectedChange(opcion) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = opcion.label,
                    style = VitalarmaType.boton,
                    color = if (activo) VitalarmaColors.textoSobreAccion else VitalarmaColors.textoSecundario
                )
            }
        }
    }
}

private fun ciclar(valor: Int, minimo: Int, maximo: Int): Int {
    val rango = maximo - minimo + 1
    return ((valor - minimo) % rango + rango) % rango + minimo
}

private fun formatoHora(valor: Int): String = ciclar(valor, 1, 12).toString()

private fun formatoMinuto(valor: Int): String =
    ciclar(valor, 0, 59).toString().padStart(2, '0')
