package com.vitalarma.mobile.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.vitalarma.mobile.model.BackupDevice
import com.vitalarma.mobile.model.sampleDevices
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.HeaderBack
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

@Composable
fun DevicesScreen(
    onBackClick: () -> Unit,
    onLinkDeviceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var devices by remember { mutableStateOf(sampleDevices) }

    Column(modifier = modifier.fillMaxSize()) {
        HeaderBack(title = "Dispositivos", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin)
        ) {
            Text(
                text = "Cambia el orden para decidir a quién se avisa primero",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoSecundario
            )

            Spacer(Modifier.height(Dimens.SpacingBase))

            devices.forEachIndexed { indice, device ->
                DeviceCard(
                    device = device,
                    posicion = indice + 1,
                    puedeSubir = indice > 0,
                    puedeBajar = indice < devices.lastIndex,
                    onMover = { direccion ->
                        devices = moverIntercambiandoTiempos(devices, indice, direccion)
                    }
                )

                if (indice < devices.lastIndex) {
                    Spacer(Modifier.height(Dimens.SpacingMd))
                }
            }

            Spacer(Modifier.height(Dimens.SpacingXl))

            Text(
                text = "El respaldo solo se activa en alarmas críticas",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoAccion,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VitalarmaColors.fondoAccionSuave, VitalarmaShapes.None)
                    .padding(Dimens.SpacingMd)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.ScreenHorizontalMargin)
        ) {
            VitalarmaButton(
                text = "+ VINCULAR DISPOSITIVO",
                onClick = onLinkDeviceClick,
                hierarchy = ButtonHierarchy.Primary
            )
        }
    }
}

private fun moverIntercambiandoTiempos(
    devices: List<BackupDevice>,
    indice: Int,
    direccion: Int
): List<BackupDevice> {
    val destino = indice + direccion
    if (destino !in devices.indices) return devices

    val lista = devices.toMutableList()
    val actual = lista[indice]
    val vecino = lista[destino]

    lista[destino] = actual.copy(delayMinutes = vecino.delayMinutes)
    lista[indice] = vecino.copy(delayMinutes = actual.delayMinutes)

    return lista
}

@Composable
private fun DeviceCard(
    device: BackupDevice,
    posicion: Int,
    puedeSubir: Boolean,
    puedeBajar: Boolean,
    onMover: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
            .border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.None)
            .padding(Dimens.SpacingBase),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.Spacing2xl)
                .background(VitalarmaColors.fondoAccion, VitalarmaShapes.Circle),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$posicion°",
                style = VitalarmaType.label,
                color = VitalarmaColors.textoSobreAccion
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = device.name,
                style = VitalarmaType.bodyStrong,
                color = VitalarmaColors.textoPrimario
            )

            Text(
                text = "${device.kind} · avisar a los ${device.delayMinutes} min",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoSecundario,
                modifier = Modifier.padding(top = Dimens.Spacing2xs)
            )

            Text(
                text = if (device.connected) "Conectado" else "Sin conexión",
                style = VitalarmaType.caption,
                color = if (device.connected) {
                    VitalarmaColors.estadoExito
                } else {
                    VitalarmaColors.textoTerciario
                },
                modifier = Modifier.padding(top = Dimens.Spacing2xs)
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)) {
            FlechaOrden(
                icono = Icons.Default.KeyboardArrowUp,
                descripcion = "Subir ${device.name}",
                habilitada = puedeSubir,
                onClick = { onMover(-1) }
            )

            FlechaOrden(
                icono = Icons.Default.KeyboardArrowDown,
                descripcion = "Bajar ${device.name}",
                habilitada = puedeBajar,
                onClick = { onMover(1) }
            )
        }
    }
}

@Composable
private fun FlechaOrden(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    descripcion: String,
    habilitada: Boolean,
    onClick: () -> Unit
) {
    Icon(
        imageVector = icono,
        contentDescription = descripcion,
        tint = if (habilitada) VitalarmaColors.textoAccion else VitalarmaColors.bordeFuerte,
        modifier = Modifier
            .size(Dimens.IconSize)
            .clickable(enabled = habilitada) { onClick() }
            .semantics { contentDescription = descripcion }
    )
}
