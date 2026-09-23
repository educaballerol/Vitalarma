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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.model.Routine
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.HeaderBack
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

@Composable
fun DeleteRoutineScreen(
    routine: Routine,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    onConfirmDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        HeaderBack(title = "Eliminar rutina", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingBase),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingBase)
        ) {
            Text(
                text = "¿Estás seguro de que quieres eliminar esta rutina?",
                style = VitalarmaType.bodyLarge,
                color = VitalarmaColors.textoPrimario
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
                    .border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.None)
                    .padding(Dimens.SpacingBase)
            ) {
                Text(
                    text = routine.name,
                    style = VitalarmaType.bodyStrong,
                    color = VitalarmaColors.textoPrimario
                )

                Text(
                    text = "${routine.alarmCount} alarmas · ${routine.daysLabel}",
                    style = VitalarmaType.bodySmall,
                    color = VitalarmaColors.textoSecundario,
                    modifier = Modifier.padding(top = Dimens.Spacing2xs)
                )
            }

            Text(
                text = "Las ${routine.alarmCount} alarmas no se borran: quedan sueltas en " +
                    "Mis Alarmas y siguen sonando por su cuenta.",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoAccion,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VitalarmaColors.fondoAccionSuave, VitalarmaShapes.None)
                    .padding(Dimens.SpacingMd)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VitalarmaColors.fondoPagina, VitalarmaShapes.None)
                    .border(Dimens.BorderThin, VitalarmaColors.estadoError, VitalarmaShapes.None)
                    .padding(Dimens.SpacingBase),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = VitalarmaColors.estadoError,
                    modifier = Modifier.size(Dimens.IconSize)
                )

                Text(
                    text = "Esta acción no se puede deshacer",
                    style = VitalarmaType.bodyStrong,
                    color = VitalarmaColors.estadoError,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.weight(1f))

            VitalarmaButton(
                text = "Cancelar",
                onClick = onCancelClick,
                hierarchy = ButtonHierarchy.Secondary
            )

            VitalarmaButton(
                text = "Sí, eliminar",
                onClick = onConfirmDeleteClick,
                hierarchy = ButtonHierarchy.Danger
            )
        }
    }
}
