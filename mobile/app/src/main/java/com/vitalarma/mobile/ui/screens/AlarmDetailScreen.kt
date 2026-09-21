package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.vitalarma.mobile.ui.components.AlarmCard
import com.vitalarma.mobile.ui.components.DayChip
import com.vitalarma.mobile.ui.components.HeaderBack
import com.vitalarma.mobile.ui.components.SettingsRow
import com.vitalarma.mobile.ui.components.TypePickerSheet
import com.vitalarma.mobile.ui.components.VitalarmaBottomNavBar
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.components.VitalarmaTextField
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

private val weekDays = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

/**
 * M-05 · Editar alarma. Incluye el selector de días, el campo de título,
 * las filas de ajuste (Tipo, Se apaga, Respaldo, Área de vida) y el
 * bottom sheet de M-06b, que se abre al tocar la fila "Tipo".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmDetailScreen(
    alarm: Alarm,
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var type by remember { mutableStateOf(alarm.type) }
    var title by remember { mutableStateOf(alarm.title) }
    var selectedDays by remember { mutableStateOf(setOf("Lun", "Mar", "Mié", "Jue", "Vie")) }
    var switchOn by remember { mutableStateOf(alarm.enabled) }
    var showTypeSheet by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        HeaderBack(title = "Editar alarma", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingBase),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingBase)
        ) {
            AlarmCard(
                time = alarm.time,
                type = type,
                description = title,
                checked = switchOn,
                onCheckedChange = { switchOn = it },
                onClick = {}
            )

            Text(text = "Repetir", style = VitalarmaType.label, color = VitalarmaColors.textoSecundario)
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

            VitalarmaTextField(
                label = "Título de la alarma",
                value = title,
                onValueChange = { title = it },
                placeholder = "Nombra tu alarma"
            )

            Column {
                SettingsRow(
                    title = "Tipo",
                    subtitle = "${type.label.lowercase().replaceFirstChar { it.uppercase() }} · sirena ascendente",
                    onClick = { showTypeSheet = true }
                )
                SettingsRow(title = "Se apaga", subtitle = "Escaneando un objeto", onClick = { })
                SettingsRow(title = "Respaldo", subtitle = "Reloj 5 min · Eduardo 10 min", onClick = { })
                SettingsRow(title = "Área de vida", subtitle = "Trabajo", onClick = { })
            }

            VitalarmaButton(text = "Guardar", onClick = onSaveClick)

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Eliminar alarma",
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

    if (showTypeSheet) {
        TypePickerSheet(
            selected = type,
            onDismiss = { showTypeSheet = false },
            onConfirm = { newType ->
                type = newType
                showTypeSheet = false
            }
        )
    }
}
