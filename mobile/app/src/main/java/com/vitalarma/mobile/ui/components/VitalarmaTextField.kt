package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaShapes
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * "Campo" de Figma (nodo 2111:446): label en mayúsculas arriba, caja con
 * fondo gris/10 y borde inferior grueso, placeholder en gris/50. Campo de
 * texto REAL (BasicTextField) — se puede escribir y borrar, no es una
 * imagen ni un estado fijo.
 */
@Composable
fun VitalarmaTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isPassword: Boolean = false,
    supportingText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label.uppercase(),
            style = VitalarmaType.label,
            color = VitalarmaColors.textoSecundario,
            modifier = Modifier.padding(bottom = Dimens.SpacingXs)
        )
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.TextFieldHeight)
                .background(VitalarmaColors.fondoCapa, VitalarmaShapes.None)
                .border(androidx.compose.foundation.BorderStroke(Dimens.BorderThin, VitalarmaColors.bordeFuerte), VitalarmaShapes.None)
                // TODO: Figma solo pinta el borde INFERIOR (border-b), no los 4 lados.
                // Esto es una aproximación válida para maquetación; si quieres el
                // detalle exacto, cambia este .border() por un Modifier.drawBehind
                // que dibuje una sola línea en la parte inferior.
                .padding(horizontal = Dimens.SpacingBase),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(text = placeholder, style = VitalarmaType.body, color = VitalarmaColors.textoTerciario)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = VitalarmaType.body.copy(color = VitalarmaColors.textoPrimario),
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                modifier = Modifier.fillMaxWidth()
            )
        }
        // TODO: Figma define un estado "Foco" (borde 2px naranja en los 4 lados)
        // y "Error" (borde inferior rojo). Si quieres implementarlos, envuelve el
        // Box de arriba en un `interactionSource` + `collectIsFocusedAsState()` y
        // cambia el color del borde condicionalmente.
        if (supportingText != null) {
            Text(
                text = supportingText,
                style = VitalarmaType.caption,
                color = VitalarmaColors.textoTerciario,
                modifier = Modifier.padding(top = Dimens.SpacingXs)
            )
        }
    }
}
