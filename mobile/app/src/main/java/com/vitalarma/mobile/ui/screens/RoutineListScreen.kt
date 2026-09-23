package com.vitalarma.mobile.ui.screens.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.model.Routine
import com.vitalarma.mobile.model.sampleRoutines
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.HeaderLarge
import com.vitalarma.mobile.ui.components.VitalarmaBottomNavBar
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.components.VitalarmaSwitch
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

@Composable
fun RoutineListScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onRoutineClick: (Routine) -> Unit,
    onCreateRoutineClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var routines by remember { mutableStateOf(sampleRoutines) }
    val activas = routines.count { it.enabled }

    Column(modifier = modifier.fillMaxSize()) {
        HeaderLarge(
            title = "Mis Rutinas",
            subtitle = "$activas de ${routines.size} activas"
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd),
            contentPadding = PaddingValues(top = Dimens.SpacingXs, bottom = Dimens.SpacingXl)
        ) {
            items(routines, key = { it.id }) { routine ->
                RoutineCard(
                    routine = routine,
                    onClick = { onRoutineClick(routine) },
                    onToggle = { activada ->
                        routines = routines.map {
                            if (it.id == routine.id) it.copy(enabled = activada) else it
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Dimens.ScreenHorizontalMargin,
                    end = Dimens.ScreenHorizontalMargin,
                    bottom = Dimens.SpacingBase
                )
        ) {
            VitalarmaButton(
                text = "+ CREAR RUTINA",
                onClick = onCreateRoutineClick,
                hierarchy = ButtonHierarchy.Primary
            )
        }

        VitalarmaBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate)
    }
}

@Composable
private fun RoutineCard(
    routine: Routine,
    onClick: () -> Unit,
    onToggle: (Boolean) -> Unit
) {
    val fondo = if (routine.enabled) VitalarmaColors.fondoCapa else VitalarmaColors.fondoPagina
    val tituloColor =
        if (routine.enabled) VitalarmaColors.textoPrimario else VitalarmaColors.textoTerciario

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(fondo, VitalarmaShapes.None)
            .border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.None)
            .clickable { onClick() }
            .padding(Dimens.SpacingBase),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = routine.name,
                style = VitalarmaType.bodyStrong,
                color = tituloColor
            )

            Text(
                text = "${routine.alarmCount} alarmas · ${routine.daysLabel}",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoSecundario,
                modifier = Modifier.padding(top = Dimens.Spacing2xs)
            )

            if (!routine.enabled) {
                Text(
                    text = "Apagada · sus alarmas no suenan",
                    style = VitalarmaType.caption,
                    color = VitalarmaColors.textoTerciario,
                    modifier = Modifier.padding(top = Dimens.Spacing2xs)
                )
            }
        }

        VitalarmaSwitch(
            checked = routine.enabled,
            onCheckedChange = onToggle
        )
    }
}
