package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

private data class PasoRespaldo(
    val hora: String,
    val titulo: String,
    val detalle: String,
    val enviado: Boolean
)

@Composable
fun BackupTriggeredScreen(
    alarm: Alarm,
    onAwakeClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pasos = listOf(
        PasoRespaldo("6:00", "Sonó la alarma", alarm.title, false),
        PasoRespaldo("6:05", "Avisamos a tu reloj", "Reloj de Humberto · Wear OS", true),
        PasoRespaldo("6:10", "Avisamos a Eduardo C.", "Mensaje de texto · sin ubicación", true)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VitalarmaColors.fondoPagina)
            .padding(horizontal = Dimens.ScreenHorizontalMargin)
    ) {
        Spacer(Modifier.height(Dimens.Spacing2xl))

        Text(
            text = "Respaldo activado",
            style = VitalarmaType.h1,
            color = VitalarmaColors.textoPrimario,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(Dimens.SpacingSm))

        Text(
            text = "No reaccionaste a la alarma crítica",
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoSecundario,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(Dimens.SpacingXl))

        pasos.forEachIndexed { indice, paso ->
            PasoEnLinea(paso = paso, esUltimo = indice == pasos.lastIndex)
        }

        Spacer(Modifier.height(Dimens.SpacingBase))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
                .padding(Dimens.SpacingBase),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tu contacto recibe un mensaje de aviso, nunca tu ubicación",
                style = VitalarmaType.caption,
                color = VitalarmaColors.textoSecundario,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.weight(1f))

        VitalarmaButton(
            text = "ESTOY DESPIERTO",
            onClick = onAwakeClick,
            hierarchy = ButtonHierarchy.Primary
        )

        Spacer(Modifier.height(Dimens.SpacingBase))

        Text(
            text = "Cancelar los avisos pendientes",
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoSecundario,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCancelClick() }
                .padding(vertical = Dimens.SpacingXs)
        )

        Spacer(Modifier.height(Dimens.SpacingXl))
    }
}

@Composable
private fun PasoEnLinea(paso: PasoRespaldo, esUltimo: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier.width(18.dp).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(VitalarmaColors.fondoAccion, VitalarmaShapes.Circle)
            )

            if (!esUltimo) {
                Box(
                    modifier = Modifier
                        .width(Dimens.BorderThick)
                        .fillMaxHeight()
                        .background(VitalarmaColors.bordeFuerte)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = Dimens.SpacingMd, bottom = Dimens.SpacingXl),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing2xs)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
            ) {
                Text(
                    text = paso.hora,
                    style = VitalarmaType.dato,
                    color = VitalarmaColors.textoPrimario
                )

                Text(
                    text = paso.titulo,
                    style = VitalarmaType.body,
                    color = VitalarmaColors.textoPrimario,
                    modifier = Modifier.weight(1f)
                )

                if (paso.enviado) {
                    InsigniaEnviado()
                }
            }

            Text(
                text = paso.detalle,
                style = VitalarmaType.caption,
                color = VitalarmaColors.textoSecundario
            )
        }
    }
}

@Composable
private fun InsigniaEnviado() {
    Box(
        modifier = Modifier
            .border(Dimens.BorderThin, VitalarmaColors.estadoExito, VitalarmaShapes.None)
            .padding(horizontal = Dimens.SpacingXs, vertical = 2.dp)
    ) {
        Text(
            text = "ENVIADO",
            style = VitalarmaType.label,
            color = VitalarmaColors.estadoExito
        )
    }
}
