package com.medcalculator.wear.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text
import com.medcalculator.wear.R

/**
 * Shows the current dosage/concentration for the selected medicine. Both values are
 * "semi-standard": prefilled from the catalog default but meant to be editable via presets
 * or a custom picker (see the design mockups) — that editing sub-flow is a follow-up, this
 * screen currently only displays the default values before moving on to weight entry.
 */
@Composable
fun ParametriScreen(
    viewModel: CalculatorViewModel,
    onNext: () -> Unit,
) {
    val medicine = viewModel.selectedMedicine

    ThreeBandScaffold(
        bottomBand = {
            Chip(
                onClick = onNext,
                enabled = medicine != null,
                label = { Text(stringResource(R.string.action_next)) },
                colors = ChipDefaults.primaryChipColors(),
                modifier = Modifier.fillMaxSize(),
            )
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = medicine?.name.orEmpty(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 2.dp),
            )
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                ParamValue(
                    label = stringResource(R.string.label_dosage),
                    value = viewModel.currentDosageMgPerKg?.let {
                        stringResource(R.string.unit_mg_per_kg, formatValue(it))
                    }.orEmpty(),
                    modifier = Modifier.weight(1f),
                )
                ParamValue(
                    label = stringResource(R.string.label_concentration),
                    value = viewModel.currentConcentrationMgPerMl?.let {
                        stringResource(R.string.unit_mg_per_ml, formatValue(it))
                    }.orEmpty(),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun ParamValue(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = label, textAlign = TextAlign.Center)
        Text(text = value, textAlign = TextAlign.Center)
    }
}
