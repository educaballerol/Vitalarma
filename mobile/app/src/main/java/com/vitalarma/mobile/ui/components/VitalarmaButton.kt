package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * "Botón" de Figma (nodo 2110:465). Jerarquía = peso visual:
 * Primario > Secundario > Terciario, más Peligro para acciones destructivas.
 * Esquinas rectas siempre. Ancho FILL (fillMaxWidth) salvo que el layout
 * lo ponga en una fila de dos (ver Row + weight en la pantalla).
 */
enum class ButtonHierarchy { Primary, Secondary, Tertiary, Danger }

@Composable
fun VitalarmaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    hierarchy: ButtonHierarchy = ButtonHierarchy.Primary,
    enabled: Boolean = true
) {
    val (bg, border, textColor) = when (hierarchy) {
        ButtonHierarchy.Primary -> Triple(VitalarmaColors.fondoAccion, null, VitalarmaColors.textoSobreAccion)
        ButtonHierarchy.Secondary -> Triple(
            VitalarmaColors.fondoPagina,
            BorderStroke(Dimens.BorderThin, VitalarmaColors.bordeInteractivo),
            VitalarmaColors.textoAccion
        )
        ButtonHierarchy.Tertiary -> Triple(
            VitalarmaColors.fondoPagina,
            BorderStroke(Dimens.BorderThin, VitalarmaColors.bordeSutil), // TODO: Figma usa trazo discontinuo (dashed); Compose no tiene dashed nativo en border(), usar Canvas/drawBehind con PathEffect.dashPathEffect si el profesor exige el guión exacto
            VitalarmaColors.textoSecundario
        )
        ButtonHierarchy.Danger -> Triple(VitalarmaColors.fondoPeligro, null, VitalarmaColors.textoSobrePeligro)
    }

    var mod = modifier
        .fillMaxWidth()
        .height(Dimens.ButtonHeight)
        .background(bg, VitalarmaShapes.None)
    if (border != null) mod = mod.border(border, VitalarmaShapes.None)
    mod = mod
        .clickable(enabled = enabled) { onClick() }
        .semantics { role = Role.Button }

    androidx.compose.foundation.layout.Box(modifier = mod, contentAlignment = Alignment.Center) {
        Text(text = text.uppercase(), style = VitalarmaType.boton, color = textColor)
    }
}
