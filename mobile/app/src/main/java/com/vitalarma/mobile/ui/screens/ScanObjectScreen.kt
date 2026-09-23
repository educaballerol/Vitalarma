package com.vitalarma.mobile.ui.screens.alarms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vitalarma.mobile.ui.components.ButtonHierarchy
import com.vitalarma.mobile.ui.components.VitalarmaButton
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

private val OBJETOS = listOf("TAZA", "LLAVES", "CEPILLO DE DIENTES", "ZAPATO", "BOTELLA")

@Composable
fun ScanObjectScreen(
    onScanSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var objeto by remember { mutableStateOf(OBJETOS.first()) }
    var pidiendoCambio by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().background(VitalarmaColors.fondoPagina)) {
        BandaSuperior(objeto = objeto)

        Visor(
            objeto = objeto,
            onScanSuccess = onScanSuccess,
            modifier = Modifier.weight(1f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.ScreenHorizontalMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
            ) {
                Box(
                    modifier = Modifier
                        .size(Dimens.SpacingSm)
                        .background(VitalarmaColors.tipoCritica, VitalarmaShapes.Circle)
                )

                Text(
                    text = "La alarma sigue sonando",
                    style = VitalarmaType.h3,
                    color = VitalarmaColors.textoPrimario
                )
            }

            Text(
                text = "Apunta la cámara al objeto para apagarla",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoSecundario
            )

            Spacer(Modifier.height(Dimens.SpacingBase))

            Text(
                text = "No tengo la ${objeto.lowercase()} a la mano, escoger otro objeto",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoSecundario,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { pidiendoCambio = true }
                    .padding(vertical = Dimens.SpacingXs)
            )
        }

        Spacer(Modifier.height(Dimens.SpacingXl))
    }

    if (pidiendoCambio) {
        ConfirmarCambioObjeto(
            objeto = objeto,
            onConfirm = {
                objeto = OBJETOS.filter { it != objeto }.random()
                pidiendoCambio = false
            },
            onDismiss = { pidiendoCambio = false }
        )
    }
}

@Composable
private fun BandaSuperior(objeto: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(VitalarmaColors.fondoSuperposicion)
            .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingBase),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing2xs)
    ) {
        Text(
            text = "Escanea una $objeto para apagarla",
            style = VitalarmaType.bodyStrong,
            color = VitalarmaColors.fondoPagina,
            textAlign = TextAlign.Center
        )

        Text(
            text = "La app eligió el objeto al azar",
            style = VitalarmaType.caption,
            color = VitalarmaColors.fondoCapaAlta,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Visor(
    objeto: String,
    onScanSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(VitalarmaColors.textoSecundario)
            .clickable { onScanSuccess() },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(240.dp), contentAlignment = Alignment.Center) {
            EsquinaEnfoque(Alignment.TopStart)
            EsquinaEnfoque(Alignment.TopEnd)
            EsquinaEnfoque(Alignment.BottomStart)
            EsquinaEnfoque(Alignment.BottomEnd)

            Text(
                text = objeto,
                style = VitalarmaType.label,
                color = VitalarmaColors.fondoCapaAlta
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimens.Spacing2xl)
                .background(VitalarmaColors.fondoSuperposicion, VitalarmaShapes.None)
                .padding(horizontal = Dimens.SpacingBase, vertical = Dimens.SpacingXs)
        ) {
            Text(
                text = "Buscando el objeto…",
                style = VitalarmaType.caption,
                color = VitalarmaColors.fondoPagina
            )
        }
    }
}

@Composable
private fun BoxScope.EsquinaEnfoque(alineacion: Alignment) {
    val grosor = 3.dp
    val largo = 36.dp
    val color = VitalarmaColors.fondoPagina

    val arriba = alineacion == Alignment.TopStart || alineacion == Alignment.TopEnd
    val izquierda = alineacion == Alignment.TopStart || alineacion == Alignment.BottomStart

    Box(modifier = Modifier.align(alineacion).size(largo)) {
        Box(
            modifier = Modifier
                .align(if (arriba) Alignment.TopStart else Alignment.BottomStart)
                .fillMaxWidth()
                .height(grosor)
                .background(color)
        )
        Box(
            modifier = Modifier
                .align(if (izquierda) Alignment.TopStart else Alignment.TopEnd)
                .width(grosor)
                .height(largo)
                .background(color)
        )
    }
}

@Composable
private fun ConfirmarCambioObjeto(
    objeto: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(VitalarmaColors.fondoPagina, VitalarmaShapes.None)
                .border(Dimens.BorderThin, VitalarmaColors.bordeSutil, VitalarmaShapes.None)
                .padding(Dimens.SpacingXl),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
        ) {
            Text(
                text = "¿No tienes la ${objeto.lowercase()}?",
                style = VitalarmaType.h3,
                color = VitalarmaColors.textoPrimario
            )

            Text(
                text = "La alarma no se apaga sin escanear algo. Podemos cambiar el objeto, " +
                    "pero vas a tener que buscar el nuevo.",
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoSecundario
            )

            Spacer(Modifier.height(Dimens.SpacingXs))

            VitalarmaButton(
                text = "ESCOGER OTRO OBJETO",
                onClick = onConfirm,
                hierarchy = ButtonHierarchy.Primary
            )

            VitalarmaButton(
                text = "SEGUIR BUSCANDO",
                onClick = onDismiss,
                hierarchy = ButtonHierarchy.Tertiary
            )
        }
    }
}
