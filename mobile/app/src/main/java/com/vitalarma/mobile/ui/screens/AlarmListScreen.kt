package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.model.Alarm
import com.vitalarma.mobile.model.AlarmType
import com.vitalarma.mobile.model.sampleAlarms
import com.vitalarma.mobile.ui.components.AlarmCard
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.FilterChipItem
import com.vitalarma.mobile.ui.components.HeaderLarge
import com.vitalarma.mobile.ui.components.VitalarmaBottomNavBar
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens

private enum class AlarmFilter(val label: String) {
    TODAS("Todas"), CRITICAS("Críticas"), CAUTAS("Cautas"), RECORDATORIOS("Recordatorios")
}

/**
 * M-04 · Mis alarmas. Lista con filtros por tipo, cada tarjeta con su
 * switch real (estado en memoria, sin persistencia — es maquetación).
 */
@Composable
fun AlarmListScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onAlarmClick: (Alarm) -> Unit,
    onCreateAlarmClick: () -> Unit,
    onTestCriticalClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var filter by remember { mutableStateOf(AlarmFilter.TODAS) }
    var alarms by remember { mutableStateOf(sampleAlarms) }

    val visibleAlarms = alarms.filter {
        when (filter) {
            AlarmFilter.TODAS -> true
            AlarmFilter.CRITICAS -> it.type == AlarmType.CRITICA
            AlarmFilter.CAUTAS -> it.type == AlarmType.CAUTA
            AlarmFilter.RECORDATORIOS -> it.type == AlarmType.RECORDATORIO
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        HeaderLarge(title = "Mis Alarmas", subtitle = "Próxima: 6:00 A.M · en 2 h")

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(top = Dimens.SpacingBase, bottom = Dimens.SpacingXl)
        ) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                    AlarmFilter.values().forEach { f ->
                        FilterChipItem(label = f.label, selected = filter == f, onClick = { filter = f })
                    }
                }
            }
            items(visibleAlarms, key = { it.id }) { alarm ->
                AlarmCard(
                    time = alarm.time,
                    type = alarm.type,
                    description = alarm.title,
                    checked = alarm.enabled,
                    onCheckedChange = { newValue ->
                        alarms = alarms.map { if (it.id == alarm.id) it.copy(enabled = newValue) else it }
                    },
                    onClick = { onAlarmClick(alarm) }
                )
            }
            item {
                VitalarmaButton(
                    text = "Probar alarma crítica",
                    onClick = onTestCriticalClick,
                    hierarchy = ButtonHierarchy.Tertiary
                )
            }
            item {
                VitalarmaButton(
                    text = "+ Crear alarma",
                    onClick = onCreateAlarmClick,
                    hierarchy = ButtonHierarchy.Primary
                )
            }
        }

        VitalarmaBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate)
    }
}