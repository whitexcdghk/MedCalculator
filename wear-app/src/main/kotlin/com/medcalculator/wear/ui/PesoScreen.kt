package com.medcalculator.wear.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState
import com.medcalculator.wear.R

private const val MaxKg = 60
private const val TenthsPerKg = 10

/**
 * Weight entry: a two-column picker (kg | tenths of a kg), the same pattern as the Wear OS
 * system timer duration picker, matching the design mockups.
 */
@Composable
fun PesoScreen(
    onConfirm: (weightKg: Double) -> Unit,
) {
    val kgState = rememberPickerState(initialNumberOfOptions = MaxKg)
    val hgState = rememberPickerState(initialNumberOfOptions = TenthsPerKg)
    val kgDescription = stringResource(R.string.picker_kg_description)
    val hgDescription = stringResource(R.string.picker_hg_description)

    ThreeBandScaffold(
        bottomBand = {
            Chip(
                onClick = {
                    val weightKg = kgState.selectedOption + hgState.selectedOption / 10.0
                    onConfirm(weightKg)
                },
                label = { Text(stringResource(R.string.action_ok)) },
                colors = ChipDefaults.primaryChipColors(),
                modifier = Modifier.fillMaxSize(),
            )
        },
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Picker(
                state = kgState,
                contentDescription = kgDescription,
                modifier = Modifier.weight(1f),
            ) { index -> Text("$index") }
            Picker(
                state = hgState,
                contentDescription = hgDescription,
                modifier = Modifier.weight(1f),
            ) { index -> Text("$index") }
        }
    }
}

@Preview(device = WearRoundPreviewDevice, showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PesoScreenPreview() {
    MaterialTheme {
        PesoScreen(onConfirm = {})
    }
}
