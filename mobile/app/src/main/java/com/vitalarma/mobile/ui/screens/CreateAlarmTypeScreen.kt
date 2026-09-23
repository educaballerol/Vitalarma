package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.model.AlarmType
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.StepHeader
import com.vitalarma.mobile.ui.components.TypeSelector
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

@Composable
fun CreateAlarmTypeScreen(
    onBackClick: () -> Unit,
    onNextClick: (AlarmType) -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var tipo by remember { mutableStateOf(AlarmType.CRITICA) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        StepHeader(
            title = "Escoger tipo",
            currentStep = 2,
            totalSteps = 3,
            onBackClick = onBackClick
        )

        Spacer(Modifier.padding(top = Dimens.SpacingXl))

        Text(
            text = "El tipo decide cómo suena y qué hace falta para apagarla.",
            style = VitalarmaType.bodySmall,
            color = VitalarmaColors.textoSecundario,
            modifier = Modifier.padding(horizontal = Dimens.ScreenHorizontalMargin)
        )

        Spacer(Modifier.padding(top = Dimens.SpacingBase))

        TypeSelector(
            selected = tipo,
            onSelectedChange = { tipo = it },
            modifier = Modifier.padding(horizontal = Dimens.ScreenHorizontalMargin)
        )

        Spacer(Modifier.padding(top = Dimens.Spacing2xl))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.ScreenHorizontalMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
        ) {
            VitalarmaButton(
                text = "SIGUIENTE",
                onClick = { onNextClick(tipo) },
                hierarchy = ButtonHierarchy.Primary
            )

            VitalarmaButton(
                text = "CANCELAR",
                onClick = onCancelClick,
                hierarchy = ButtonHierarchy.Tertiary
            )
        }
    }
}
