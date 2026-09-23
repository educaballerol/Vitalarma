package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * "Encabezado" de Figma (nodo 2116:462): "Grande para las raíces de cada
 * pestaña (H1 + próxima acción), Atrás para pantallas de detalle, Modal
 * para los pasos del flujo de creación." Grande y Atrás las usan
 * M-04/05/06/16; Modal la usa M-17.
 */

/** Variante "Grande": H1 + subtítulo opcional. Usada en M-04 (Mis Alarmas). */
@Composable
fun HeaderLarge(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(VitalarmaColors.fondoPagina)
            .padding(
                start = Dimens.ScreenHorizontalMargin,
                end = Dimens.ScreenHorizontalMargin,
                top = Dimens.SpacingSm,
                bottom = Dimens.SpacingBase
            )
    ) {
        Text(text = title, style = VitalarmaType.h1, color = VitalarmaColors.textoPrimario)
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = VitalarmaType.bodySmall,
                color = VitalarmaColors.textoSecundario,
                modifier = Modifier.padding(top = Dimens.SpacingXs)
            )
        }
    }
}

/** Variante "Atrás": flecha + H3. Usada en M-05 (Editar alarma) y M-06 (Eliminar alarma). */
@Composable
fun HeaderBack(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(VitalarmaColors.fondoPagina)
            .padding(horizontal = Dimens.ScreenHorizontalMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Volver",
            tint = VitalarmaColors.textoPrimario,
            modifier = Modifier
                .size(Dimens.IconSize)
                .clickable { onBackClick() }
        )
        Text(
            text = title,
            style = VitalarmaType.h3,
            color = VitalarmaColors.textoPrimario,
            modifier = Modifier.padding(start = Dimens.SpacingBase)
        )
    }
}

/** Variante "Modal": solo título, sin flecha atrás ni botón de cerrar. Usada en los pasos del flujo de creación (M-17). */
@Composable
fun HeaderModal(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(VitalarmaColors.fondoPagina)
            .padding(horizontal = Dimens.ScreenHorizontalMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = VitalarmaType.h3,
            color = VitalarmaColors.textoPrimario
        )
    }
}
