package com.vitalarma.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitalarma.mobile.model.AlarmType
import com.vitalarma.mobile.ui.theme.Dimens
import com.vitalarma.mobile.ui.theme.VitalarmaColors
import com.vitalarma.mobile.ui.theme.VitalarmaType

/**
 * M-06b · Escoge un tipo. Bottom sheet MODAL real (Material3
 * ModalBottomSheet, no una imagen ni un overlay falso): el "Velo" del
 * Figma corresponde al scrim que ModalBottomSheet ya dibuja solo.
 * Confirmar aplica el cambio; Cancelar descarta la selección temporal.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypePickerSheet(
    selected: AlarmType,
    onDismiss: () -> Unit,
    onConfirm: (AlarmType) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    var localSelection by remember { mutableStateOf(selected) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = VitalarmaColors.fondoPagina,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.ScreenHorizontalMargin, vertical = Dimens.SpacingMd),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Dimens.SpacingMd)
        ) {
            Text(text = "Escoge un tipo", style = VitalarmaType.h3, color = VitalarmaColors.textoPrimario)

            TypeSelector(selected = localSelection, onSelectedChange = { localSelection = it })

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Dimens.SpacingMd)
            ) {
                VitalarmaButton(
                    text = "Cancelar",
                    onClick = onDismiss,
                    hierarchy = ButtonHierarchy.Secondary,
                    modifier = Modifier.weight(1f)
                )
                VitalarmaButton(
                    text = "Confirmar",
                    onClick = { onConfirm(localSelection) },
                    hierarchy = ButtonHierarchy.Primary,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// weight() en un Row normal necesita RowScope; lo resolvemos con este import
// implícito de Compose (androidx.compose.foundation.layout.RowScope.weight)
// que ya viene disponible dentro del bloque Row { } de arriba.
