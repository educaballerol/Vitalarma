package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.vitalarma.mobile.model.Alarm
import com.vitalarma.mobile.ui.components.AlarmCard
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.HeaderBack
import com.vitalarma.mobile.ui.components.VitalarmaBottomNavBar
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * M-06 · Eliminar alarma. Confirmación con panel de aviso en rojo
 * (borde discontinuo en Figma — ver TODO) y dos botones: Cancelar
 * (secundario) y Sí, eliminar (peligro).
 */
@Composable
fun DeleteAlarmScreen(
    alarm: Alarm,
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    onConfirmDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        HeaderBack(title = "Eliminar alarma", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingBase),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingBase)
        ) {
            Text(
                text = "¿Estás seguro de que quieres eliminar esta alarma?",
                style = VitalarmaType.bodyLarge,
                color = VitalarmaColors.textoPrimario
            )

            AlarmCard(
                time = alarm.time,
                type = alarm.type,
                description = alarm.title,
                checked = alarm.enabled,
                onCheckedChange = { },
                onClick = { }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VitalarmaColors.fondoPagina, VitalarmaShapes.None)
                    .border(Dimens.BorderThin, VitalarmaColors.estadoError, VitalarmaShapes.None)
                    // TODO: Figma usa borde DISCONTINUO (dashed) aquí. Compose no tiene
                    // dashed nativo en border(); si el profesor lo exige exacto, usa
                    // Modifier.drawBehind { drawRoundRect(..., style = Stroke(pathEffect = PathEffect.dashPathEffect(...))) }
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

            androidx.compose.foundation.layout.Spacer(Modifier.weight(1f))

            VitalarmaButton(text = "Cancelar", onClick = onCancelClick, hierarchy = ButtonHierarchy.Secondary)
            VitalarmaButton(text = "Sí, eliminar", onClick = onConfirmDeleteClick, hierarchy = ButtonHierarchy.Danger)
        }

        VitalarmaBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate)
    }
}
