package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes

@Composable
fun StepIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.ScreenHorizontalMargin)
            .clearAndSetSemantics {
                contentDescription = "Paso $currentStep de $totalSteps"
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (step in 1..totalSteps) {
            StepDot(
                completed = step < currentStep,
                current = step == currentStep
            )
            if (step < totalSteps) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimens.BorderThin)
                        .background(
                            if (step < currentStep) VitalarmaColors.fondoAccion
                            else VitalarmaColors.bordeSutil
                        )
                )
            }
        }
    }
}

@Composable
private fun StepDot(completed: Boolean, current: Boolean) {
    val size = if (current) 16.dp else 12.dp

    Box(
        modifier = Modifier
            .size(size)
            .background(
                when {
                    current || completed -> VitalarmaColors.fondoAccion
                    else -> VitalarmaColors.fondoPagina
                },
                VitalarmaShapes.Circle
            )
            .then(
                if (!current && !completed) {
                    Modifier.border(Dimens.BorderThin, VitalarmaColors.bordeFuerte, VitalarmaShapes.Circle)
                } else {
                    Modifier
                }
            )
    )
}

@Composable
fun StepHeader(
    title: String,
    currentStep: Int,
    totalSteps: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.Column(modifier = modifier.fillMaxWidth()) {
        HeaderBack(title = "", onBackClick = onBackClick)

        androidx.compose.material3.Text(
            text = title,
            style = com.vitalarma.mobile.ui.theme.VitalarmaType.h1,
            color = VitalarmaColors.textoPrimario,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Box(modifier = Modifier.height(Dimens.SpacingXl).width(Dimens.SpacingXl))

        StepIndicator(currentStep = currentStep, totalSteps = totalSteps)
    }
}
