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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.model.AlarmType
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.CheckboxRow
import com.vitalarma.mobile.ui.components.StepHeader
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.components.VitalarmaSwitch
import com.vitalarma.mobile.ui.components.VitalarmaTextField
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

/** Cómo se apaga la alarma crítica — selección única (no ambas a la vez). */
private enum class ApagadoOption { ESCANEAR_OBJETO, RESOLVER_OPERACION }

/**
 * M-09 · Detalles. Último paso del flujo de creación (3/3): nota de voz
 * opcional, vínculo con calendario, y (solo para alarmas Críticas) el
 * método para apagarla. Mismo patrón visual que M-08 (StepHeader +
 * contenido + botones primario/terciario al fondo).
 */
@Composable
fun CreateAlarmDetailsScreen(
    alarmType: AlarmType,
    onBackClick: () -> Unit,
    onCreateAlarmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var titulo by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    var hasVoiceNote by remember { mutableStateOf(false) }
    var calendarLinked by remember { mutableStateOf(false) }
    var apagadoOption by remember { mutableStateOf(ApagadoOption.ESCANEAR_OBJETO) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        StepHeader(
            title = "Detalles",
            currentStep = 3,
            totalSteps = 3,
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingXl),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXl)
        ) {

            // Título / descripción — opcional
            VitalarmaTextField(
                label = "Título",
                value = titulo,
                onValueChange = { titulo = it },
                placeholder = "Describe de qué va esta alarma",
                supportingText = "Opcional"
            )

            // Nota de voz
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                Text(text = "Nota de voz", style = VitalarmaType.label, color = VitalarmaColors.textoSecundario)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
                        .border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.None)
                        .clickable {
                            isRecording = !isRecording
                            if (!isRecording) hasVoiceNote = true
                        }
                        .padding(Dimens.SpacingBase),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dimens.IconSize + Dimens.SpacingSm)
                            .background(
                                if (isRecording) VitalarmaColors.estadoError else VitalarmaColors.fondoAccion,
                                VitalarmaShapes.Circle
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isRecording) Icons.Filled.Stop else Icons.Filled.Mic,
                            contentDescription = if (isRecording) "Detener grabación" else "Grabar nota de voz",
                            tint = VitalarmaColors.textoSobreAccion,
                            modifier = Modifier.size(Dimens.IconSize)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = when {
                                isRecording -> "Grabando..."
                                hasVoiceNote -> "Nota de voz guardada"
                                else -> "Grabar nota de voz"
                            },
                            style = VitalarmaType.body,
                            color = VitalarmaColors.textoPrimario
                        )
                        Text(
                            text = "Opcional · hasta 30 s",
                            style = VitalarmaType.caption,
                            color = VitalarmaColors.textoTerciario
                        )
                    }
                }
            }

            // Calendario
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                Text(text = "Calendario", style = VitalarmaType.label, color = VitalarmaColors.textoSecundario)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Vincular con el calendario",
                        style = VitalarmaType.body,
                        color = VitalarmaColors.textoPrimario,
                        modifier = Modifier.weight(1f)
                    )
                    VitalarmaSwitch(checked = calendarLinked, onCheckedChange = { calendarLinked = it })
                }

                if (calendarLinked) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
                            .border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.None)
                            .padding(Dimens.SpacingBase)
                    ) {
                        // Evento de ejemplo — sin conexión real a ningún calendario.
                        Text(text = "Reunión con cliente", style = VitalarmaType.bodyStrong, color = VitalarmaColors.textoPrimario)
                        Text(
                            text = "hoy · 1:00 P.M a 2:00 P.M",
                            style = VitalarmaType.bodySmall,
                            color = VitalarmaColors.textoSecundario,
                            modifier = Modifier.padding(top = Dimens.Spacing2xs)
                        )
                    }
                }
            }

            // Cómo se apaga — solo para alarmas Críticas
            if (alarmType == AlarmType.CRITICA) {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.Spacing2xs)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Cómo se apaga", style = VitalarmaType.label, color = VitalarmaColors.textoSecundario)
                        Text(
                            text = "  SOLO CRÍTICAS",
                            style = VitalarmaType.caption,
                            color = VitalarmaColors.tipoCriticaTexto
                        )
                    }
                    CheckboxRow(
                        label = "Escanear un objeto al azar",
                        checked = apagadoOption == ApagadoOption.ESCANEAR_OBJETO,
                        onCheckedChange = { if (it) apagadoOption = ApagadoOption.ESCANEAR_OBJETO }
                    )
                    CheckboxRow(
                        label = "Resolver una operación",
                        checked = apagadoOption == ApagadoOption.RESOLVER_OPERACION,
                        onCheckedChange = { if (it) apagadoOption = ApagadoOption.RESOLVER_OPERACION }
                    )
                }
            }
        }

        Spacer(Modifier.padding(top = Dimens.SpacingBase))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.ScreenHorizontalMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
        ) {
            VitalarmaButton(
                text = "CREAR ALARMA",
                onClick = onCreateAlarmClick,
                hierarchy = ButtonHierarchy.Primary
            )
            VitalarmaButton(
                text = "ATRÁS",
                onClick = onBackClick,
                hierarchy = ButtonHierarchy.Tertiary
            )
        }
    }
}
