package com.vitalarma.mobile.ui.screens.routines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.vitalarma.mobile.model.Alarm
import com.vitalarma.mobile.model.Routine
import com.vitalarma.mobile.model.sampleAlarms
import com.vitalarma.mobile.ui.components.AlarmCard
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.DayChip
import com.vitalarma.mobile.ui.components.HeaderBack
import com.vitalarma.mobile.ui.components.VitalarmaBottomNavBar
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

private val weekDays = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

/** Traduce el "daysLabel" de la rutina (ej. "Lun a Vie") a un set de días activos, solo para poblar la UI de ejemplo. */
private fun daysFromLabel(label: String): Set<String> = when {
    label.contains("Lun a Vie") -> setOf("Lun", "Mar", "Mié", "Jue", "Vie")
    label.contains("Sáb y Dom") -> setOf("Sáb", "Dom")
    label.contains("Mar y Jue") -> setOf("Mar", "Jue")
    else -> emptySet()
}

/**
 * M-16 · Detalle de rutina. Días activos (CírculoDía), lista de alarmas
 * de la rutina (TarjetaAlarma, reutilizando AlarmCard) con botón para
 * agregar más, Guardar y Eliminar rutina.
 *
 * Como Routine no guarda qué alarmas específicas la componen (es solo
 * maquetación, sin backend), se muestran las primeras `alarmCount`
 * alarmas de sampleAlarms como aproximación — igual que hace
 * AlarmListScreen con sus datos de ejemplo.
 */
@Composable
fun RoutineDetailScreen(
    routine: Routine,
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onAddAlarmClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDays by remember { mutableStateOf(daysFromLabel(routine.daysLabel)) }
    val routineAlarms: List<Alarm> = remember(routine) { sampleAlarms.take(routine.alarmCount.coerceAtMost(sampleAlarms.size)) }
    var alarmStates by remember { mutableStateOf(routineAlarms.associate { it.id to it.enabled }) }

    Column(modifier = modifier.fillMaxSize()) {
        HeaderBack(title = routine.name, onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingBase),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingBase)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                Text(text = "Activa los días", style = VitalarmaType.label, color = VitalarmaColors.textoSecundario)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    weekDays.forEach { day ->
                        DayChip(
                            label = day,
                            selected = day in selectedDays,
                            onClick = {
                                selectedDays = if (day in selectedDays) selectedDays - day else selectedDays + day
                            }
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                Text(
                    text = "${routineAlarms.size} alarmas en esta rutina",
                    style = VitalarmaType.label,
                    color = VitalarmaColors.textoSecundario
                )
                routineAlarms.forEach { alarm ->
                    AlarmCard(
                        time = alarm.time,
                        type = alarm.type,
                        description = alarm.title,
                        checked = alarmStates[alarm.id] ?: alarm.enabled,
                        onCheckedChange = { newValue ->
                            alarmStates = alarmStates.toMutableMap().apply { put(alarm.id, newValue) }
                        },
                        onClick = { }
                    )
                }
                VitalarmaButton(
                    text = "+ Agregar alarma a la rutina",
                    onClick = onAddAlarmClick,
                    hierarchy = ButtonHierarchy.Tertiary
                )
            }

            Spacer(Modifier.padding(top = Dimens.SpacingXl))

            VitalarmaButton(text = "Guardar", onClick = onSaveClick, hierarchy = ButtonHierarchy.Primary)

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Eliminar rutina",
                    style = VitalarmaType.body,
                    color = VitalarmaColors.estadoError,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(vertical = Dimens.SpacingXs)
                        .clickable { onDeleteClick() }
                )
            }
        }

        VitalarmaBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate)
    }
}
