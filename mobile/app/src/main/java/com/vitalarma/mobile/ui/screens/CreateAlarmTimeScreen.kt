package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.DayChip
import com.vitalarma.mobile.ui.components.Meridiem
import com.vitalarma.mobile.ui.components.StepHeader
import com.vitalarma.mobile.ui.components.TimeWheel
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

private val DIAS = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

@Composable
fun CreateAlarmTimeScreen(
    onBackClick: () -> Unit,
    onNextClick: (hora: String, dias: List<String>) -> Unit,
    onCreateReminderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var hora by remember { mutableIntStateOf(6) }
    var minuto by remember { mutableIntStateOf(0) }
    var meridiem by remember { mutableStateOf(Meridiem.AM) }
    var diasElegidos by remember { mutableStateOf(setOf("Sáb", "Dom")) }

    val horaFormateada = "$hora:${minuto.toString().padStart(2, '0')} ${meridiem.label}"

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        StepHeader(
            title = "Definir hora",
            currentStep = 1,
            totalSteps = 3,
            onBackClick = onBackClick
        )

        Spacer(Modifier.height(Dimens.Spacing2xl))

        TimeWheel(
            hour = hora,
            minute = minuto,
            meridiem = meridiem,
            onHourChange = { hora = it },
            onMinuteChange = { minuto = it },
            onMeridiemChange = { meridiem = it }
        )

        Spacer(Modifier.height(Dimens.Spacing2xl))

        Text(
            text = "Repetir",
            style = VitalarmaType.label,
            color = VitalarmaColors.textoSecundario,
            modifier = Modifier.padding(horizontal = Dimens.ScreenHorizontalMargin)
        )

        Spacer(Modifier.height(Dimens.SpacingSm))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DIAS.forEach { dia ->
                DayChip(
                    label = dia,
                    selected = dia in diasElegidos,
                    onClick = {
                        diasElegidos = if (dia in diasElegidos) {
                            diasElegidos - dia
                        } else {
                            diasElegidos + dia
                        }
                    }
                )
            }
        }

        Spacer(Modifier.height(Dimens.SpacingSm))

        Text(
            text = resumenRepeticion(diasElegidos),
            style = VitalarmaType.caption,
            color = VitalarmaColors.textoTerciario,
            modifier = Modifier.padding(horizontal = Dimens.ScreenHorizontalMargin)
        )

        Spacer(Modifier.height(Dimens.Spacing2xl))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.ScreenHorizontalMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
        ) {
            VitalarmaButton(
                text = "SIGUIENTE",
                onClick = { onNextClick(horaFormateada, diasElegidos.toList()) },
                hierarchy = ButtonHierarchy.Primary
            )

            VitalarmaButton(
                text = "CREAR RECORDATORIO",
                onClick = onCreateReminderClick,
                hierarchy = ButtonHierarchy.Secondary
            )
        }

        Spacer(Modifier.height(Dimens.SpacingXl))
    }
}

private fun resumenRepeticion(dias: Set<String>): String = when {
    dias.isEmpty() -> "Suena una sola vez"
    dias.size == 7 -> "Todos los días"
    dias == setOf("Sáb", "Dom") -> "Fines de semana"
    dias == setOf("Lun", "Mar", "Mié", "Jue", "Vie") -> "Entre semana"
    else -> DIAS.filter { it in dias }.joinToString(" · ")
}
