package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.model.Alarm
import com.vitalarma.mobile.ui.components.AlarmCard
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

// TODO: no extraído de Figma todavía — placeholder con el verde de Carbon
// (green-50). Si tienes el link del nodo de M-10, lo saco exacto y lo muevo
// a VitalarmaColors junto a los demás tokens.
private val colorExito = Color(0xFF24A148)

/**
 * M-10 · Alarma creada. Confirmación al terminar el flujo M-07 → M-08 →
 * M-09: checkmark, resumen de la alarma (tarjeta + detalle) y dos
 * acciones ("Ver mis alarmas" primaria, "Crear otra alarma" secundaria).
 *
 * Como el flujo no tiene backend, la alarma que se muestra aquí es la
 * misma de ejemplo que usa el resto de la app (sampleAlarms), tal como
 * hace AlarmCompletedScreen/AlarmRingingScreen — no la que el usuario
 * acabó de "escribir" en M-09 (eso se pierde al no haber estado
 * compartido entre pantallas, como el resto de esta maquetación).
 */
@Composable
fun AlarmCreatedScreen(
    alarm: Alarm,
    onSeeAlarmsClick: () -> Unit,
    onCreateAnotherClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var alarmEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingXl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(top = Dimens.SpacingXl))

        Box(
            modifier = Modifier
                .size(64.dp)
                .background(colorExito, VitalarmaShapes.Circle),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            text = "Alarma creada",
            style = VitalarmaType.h1,
            color = VitalarmaColors.textoPrimario,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = Dimens.SpacingBase)
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(top = Dimens.SpacingXl),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingBase)
        ) {
            AlarmCard(
                time = alarm.time,
                type = alarm.type,
                description = alarm.title,
                checked = alarmEnabled,
                onCheckedChange = { alarmEnabled = it },
                onClick = { }
            )

            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                DetailRow(label = "Título", value = alarm.title)
                DetailRow(label = "Repetir", value = alarm.repeatDays.joinToString(" a ").ifEmpty { "No se repite" })
                DetailRow(label = "Se apaga", value = "Escaneando un objeto")
                DetailRow(label = "Respaldo", value = "Reloj 5 min · Eduardo 10 min")
            }
        }

        Spacer(Modifier.padding(top = Dimens.Spacing2xl))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
        ) {
            VitalarmaButton(
                text = "Ver mis alarmas",
                onClick = onSeeAlarmsClick,
                hierarchy = ButtonHierarchy.Primary
            )
            VitalarmaButton(
                text = "Crear otra alarma",
                onClick = onCreateAnotherClick,
                hierarchy = ButtonHierarchy.Secondary
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoTerciario,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoPrimario,
            modifier = Modifier.weight(2f)
        )
    }
}
