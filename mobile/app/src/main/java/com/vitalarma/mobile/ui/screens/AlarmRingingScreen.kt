package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import com.vitalarma.mobile.model.Alarm
import com.vitalarma.mobile.model.AlarmType
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

private const val SEGUNDOS_HASTA_RESPALDO = 15

@Composable
fun AlarmRingingScreen(
    alarm: Alarm,
    onStopClick: () -> Unit,
    onBackupTriggered: () -> Unit,
    modifier: Modifier = Modifier
) {
    val esCritica = alarm.type == AlarmType.CRITICA
    val dispararRespaldo by rememberUpdatedState(onBackupTriggered)

    if (esCritica) {
        LaunchedEffect(alarm.id) {
            kotlinx.coroutines.delay(SEGUNDOS_HASTA_RESPALDO * 1000L)
            dispararRespaldo()
        }
    }
    val colorTipo = when (alarm.type) {
        AlarmType.CRITICA -> VitalarmaColors.tipoCritica
        AlarmType.CAUTA -> VitalarmaColors.tipoCauta
        AlarmType.COMPARTIDA -> VitalarmaColors.tipoCompartida
        AlarmType.RECORDATORIO -> VitalarmaColors.tipoRecordatorio
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VitalarmaColors.fondoPagina)
            .padding(horizontal = Dimens.ScreenHorizontalMargin)
    ) {
        Spacer(Modifier.height(Dimens.Spacing2xl))

        Text(
            text = "¡Alarma sonando!",
            style = VitalarmaType.h1,
            color = VitalarmaColors.textoPrimario,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(Dimens.Spacing2xl))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
        ) {
            Box(
                modifier = Modifier
                    .width(Dimens.AlarmCardLeftBarWidth)
                    .fillMaxHeight()
                    .background(colorTipo)
            )

            Column(
                modifier = Modifier.padding(Dimens.SpacingBase),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
                ) {
                    Text(
                        text = alarm.time,
                        style = VitalarmaType.dato,
                        color = VitalarmaColors.textoPrimario
                    )

                    Box(
                        modifier = Modifier
                            .size(Dimens.SpacingSm)
                            .background(colorTipo, VitalarmaShapes.Circle)
                    )

                    Text(
                        text = alarm.type.label,
                        style = VitalarmaType.label,
                        color = colorTipo
                    )
                }

                Text(
                    text = alarm.title,
                    style = VitalarmaType.bodyStrong,
                    color = VitalarmaColors.textoPrimario
                )
            }
        }

        Spacer(Modifier.height(Dimens.SpacingBase))

        if (esCritica) {
            SnoozeBloqueado()
        } else {
            VitalarmaButton(
                text = "POSPONER 5 MINUTOS",
                onClick = {},
                hierarchy = ButtonHierarchy.Secondary
            )
        }

        Spacer(Modifier.weight(1f))

        VitalarmaButton(
            text = if (esCritica) "APAGAR ESCANEANDO" else "DETENER ALARMA",
            onClick = onStopClick,
            hierarchy = ButtonHierarchy.Primary
        )

        Spacer(Modifier.height(Dimens.SpacingBase))

        Text(
            text = "Si no reaccionas en 5 minutos avisaremos a tu reloj, y a los 10 a Eduardo C.",
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoSecundario,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(Dimens.Spacing2xl))
    }
}

@Composable
private fun SnoozeBloqueado() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
            .border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.None)
            .padding(Dimens.SpacingBase)
            .semantics { disabled() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
    ) {
        Text(
            text = "POSPONER",
            style = VitalarmaType.boton,
            color = VitalarmaColors.textoTerciario
        )

        Text(
            text = "No disponible en alarmas críticas",
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoTerciario,
            textAlign = TextAlign.Center
        )
    }
}
