package com.vitalarma.mobile.ui.screens.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.DayChip
import com.vitalarma.mobile.ui.components.HeaderModal
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.components.VitalarmaTextField
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

private val weekDays = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

/**
 * M-17 · Nueva rutina. Paso de flujo (Encabezado Modal, sin BarraNav):
 * nombre, días activos y una lista de alarmas vacía por defecto (el
 * mockup muestra el estado "Todavía no hay alarmas en esta rutina").
 * "Crear rutina" y "Cancelar" ambos vuelven a Rutinas — sin backend no
 * hay diferencia real entre los dos, pero cada uno queda como su
 * propio callback por si más adelante se conectan a algo distinto.
 */
@Composable
fun CreateRoutineScreen(
    onCreateRoutineClick: () -> Unit,
    onCancelClick: () -> Unit,
    onAddAlarmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var selectedDays by remember { mutableStateOf(setOf("Lun", "Mar", "Mié", "Jue", "Vie")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeaderModal(title = "Nueva rutina")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingBase),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingBase)
        ) {
            VitalarmaTextField(
                label = "Nombre de la rutina",
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = "Nombra tu rutina"
            )

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
                Text(text = "Alarmas de la rutina", style = VitalarmaType.label, color = VitalarmaColors.textoSecundario)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VitalarmaColors.fondoPagina, VitalarmaShapes.None)
                        .border(Dimens.BorderThin, VitalarmaColors.bordeFuerte, VitalarmaShapes.None)
                        // TODO: Figma usa borde discontinuo (dashed) en este panel vacío,
                        // igual que en los botones Terciario — ver el mismo TODO en VitalarmaButton.kt.
                        .padding(horizontal = Dimens.SpacingBase, vertical = Dimens.Spacing2xl),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Alarm,
                        contentDescription = null,
                        tint = VitalarmaColors.textoTerciario,
                        modifier = Modifier
                    )
                    Text(
                        text = "Todavía no hay alarmas en esta rutina",
                        style = VitalarmaType.bodySmall,
                        color = VitalarmaColors.textoTerciario,
                        textAlign = TextAlign.Center
                    )
                }

                VitalarmaButton(
                    text = "+ Agregar alarma",
                    onClick = onAddAlarmClick,
                    hierarchy = ButtonHierarchy.Secondary
                )
            }

            Spacer(Modifier.padding(top = Dimens.SpacingXl))

            VitalarmaButton(text = "Crear rutina", onClick = onCreateRoutineClick, hierarchy = ButtonHierarchy.Primary)
            VitalarmaButton(text = "Cancelar", onClick = onCancelClick, hierarchy = ButtonHierarchy.Tertiary)
        }
    }
}
