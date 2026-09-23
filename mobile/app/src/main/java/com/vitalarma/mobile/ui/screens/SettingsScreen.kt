package com.vitalarma.mobile.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitalarma.mobile.ui.components.CheckboxRow
import com.vitalarma.mobile.ui.components.HeaderLarge
import com.vitalarma.mobile.ui.components.SettingsRow
import com.vitalarma.mobile.ui.components.VitalarmaBottomNavBar
import com.vitalarma.mobile.ui.components.VitalarmaSwitch
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

@Composable
fun SettingsScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onDevicesClick: () -> Unit,
    onContactsClick: () -> Unit,
    onAccountClick: () -> Unit,
    onScheduleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var modoCauto by remember { mutableStateOf(true) }
    var enTransporte by remember { mutableStateOf(true) }
    var reunionEnCalendario by remember { mutableStateOf(true) }
    var enBiblioteca by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        HeaderLarge(title = "Mis Ajustes")

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenHorizontalMargin)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
                    .border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.None)
                    .padding(Dimens.SpacingBase),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Modo cauto",
                        style = VitalarmaType.h3,
                        color = VitalarmaColors.textoPrimario
                    )

                    Text(
                        text = "Solo vibrar en lugares públicos",
                        style = VitalarmaType.bodySmall,
                        color = VitalarmaColors.textoSecundario,
                        modifier = Modifier.padding(top = Dimens.Spacing2xs)
                    )
                }

                VitalarmaSwitch(
                    checked = modoCauto,
                    onCheckedChange = { modoCauto = it }
                )
            }

            Spacer(Modifier.height(Dimens.SpacingBase))

            Text(
                text = "Se activa solo cuando:",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoSecundario
            )

            Spacer(Modifier.height(Dimens.SpacingXs))

            CheckboxRow(
                label = "Estoy en transporte",
                checked = enTransporte,
                onCheckedChange = { enTransporte = it },
                enabled = modoCauto
            )

            CheckboxRow(
                label = "Tengo reunión en el calendario",
                checked = reunionEnCalendario,
                onCheckedChange = { reunionEnCalendario = it },
                enabled = modoCauto
            )

            CheckboxRow(
                label = "Estoy en la biblioteca",
                checked = enBiblioteca,
                onCheckedChange = { enBiblioteca = it },
                enabled = modoCauto
            )

            Spacer(Modifier.height(Dimens.SpacingMd))

            SettingsRow(
                title = "10:00 PM - 6:00 AM",
                subtitle = "HORARIO SILENCIOSO",
                onClick = onScheduleClick
            )

            Spacer(Modifier.height(Dimens.SpacingBase))

            Text(
                text = "Las alarmas críticas ignoran el modo cauto",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoAccion,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VitalarmaColors.fondoAccionSuave, VitalarmaShapes.None)
                    .padding(Dimens.SpacingMd)
            )

            Spacer(Modifier.height(Dimens.SpacingXl))

            SettingsRow(title = "Dispositivos", onClick = onDevicesClick)
            SettingsRow(title = "Contactos", onClick = onContactsClick)
            SettingsRow(title = "Cuenta", onClick = onAccountClick)

            Spacer(Modifier.height(Dimens.SpacingXl))
        }

        VitalarmaBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate)
    }
}
