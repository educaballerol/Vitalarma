package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.model.Alarm
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

@Composable
fun AlarmCompletedScreen(
    alarm: Alarm,
    onSeeAlarmsClick: () -> Unit,
    horaApagado: String = "6:04",
    pospuesta: Boolean = false,
    cumplidas: Int = 4,
    pospuestas: Int = 1,
    fallidas: Int = 0,
    modifier: Modifier = Modifier
) {
    val area = alarm.lifeArea.ifBlank { "Sin área" }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VitalarmaColors.fondoPagina)
            .padding(horizontal = Dimens.ScreenHorizontalMargin)
    ) {
        Spacer(Modifier.height(Dimens.Spacing2xl))

        Text(
            text = "Alarma cumplida",
            style = VitalarmaType.h1,
            color = VitalarmaColors.textoPrimario,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(Dimens.SpacingXl))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(68.dp)
                .border(Dimens.BorderThick, VitalarmaColors.estadoExito, VitalarmaShapes.Circle),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = VitalarmaColors.estadoExito,
                modifier = Modifier.size(Dimens.Spacing2xl)
            )
        }

        Spacer(Modifier.height(Dimens.SpacingXl))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
                .padding(Dimens.SpacingBase),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
        ) {
            Text(
                text = alarm.title,
                style = VitalarmaType.bodyStrong,
                color = VitalarmaColors.textoPrimario
            )

            Text(
                text = "${alarm.time}  ·  $area",
                style = VitalarmaType.caption,
                color = VitalarmaColors.textoSecundario
            )

            Spacer(Modifier.height(Dimens.Spacing2xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Apagada a las $horaApagado",
                    style = VitalarmaType.bodySmall,
                    color = VitalarmaColors.textoPrimario
                )

                Text(
                    text = if (pospuesta) "Pospuesta una vez" else "Sin posponer",
                    style = VitalarmaType.caption,
                    color = VitalarmaColors.textoSecundario
                )
            }
        }

        Spacer(Modifier.height(Dimens.SpacingXl))

        Text(
            text = "Esta semana en $area",
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoPrimario
        )

        Spacer(Modifier.height(Dimens.SpacingSm))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
        ) {
            Cifra(valor = cumplidas, etiqueta = "cumplidas", modifier = Modifier.weight(1f))
            Cifra(valor = pospuestas, etiqueta = "pospuesta", modifier = Modifier.weight(1f))
            Cifra(valor = fallidas, etiqueta = "fallidas", modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(Dimens.SpacingMd))

        Text(
            text = "El registro es automático: no tienes que confirmar nada",
            style = VitalarmaType.caption,
            color = VitalarmaColors.textoSecundario
        )

        Spacer(Modifier.weight(1f))

        VitalarmaButton(
            text = "VER MIS ALARMAS",
            onClick = onSeeAlarmsClick,
            hierarchy = ButtonHierarchy.Primary
        )

        Spacer(Modifier.height(Dimens.SpacingBase))

        Text(
            text = "Ver el detalle en la versión web",
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoSecundario,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(vertical = Dimens.SpacingXs)
        )

        Spacer(Modifier.height(Dimens.SpacingXl))
    }
}

@Composable
private fun Cifra(valor: Int, etiqueta: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
            .padding(vertical = Dimens.SpacingBase),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing2xs)
    ) {
        Text(
            text = valor.toString(),
            style = VitalarmaType.h1,
            color = VitalarmaColors.textoPrimario
        )

        Text(
            text = etiqueta,
            style = VitalarmaType.caption,
            color = VitalarmaColors.textoSecundario
        )
    }
}
