package com.medcalculator.wear.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.medcalculator.shared.DoseCalculator
import com.medcalculator.wear.R

@Composable
fun RisultatoScreen(
    viewModel: CalculatorViewModel,
    onDone: () -> Unit,
) {
    val medicine = viewModel.selectedMedicine
    val dosage = viewModel.currentDosageMgPerKg
    val concentration = viewModel.currentConcentrationMgPerMl
    val weight = viewModel.weightKg

    val result = if (dosage != null && concentration != null && weight != null) {
        DoseCalculator.calculate(
            weightKg = weight,
            dosageMgPerKg = dosage,
            concentrationMgPerMl = concentration,
        )
    } else {
        null
    }

    ThreeBandScaffold(
        bottomBand = {
            Chip(
                onClick = {
                    viewModel.resetForNewCalculation()
                    onDone()
                },
                label = { Text(stringResource(R.string.action_ok)) },
                colors = ChipDefaults.primaryChipColors(),
                modifier = Modifier.fillMaxSize(),
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(
                    R.string.result_weight_label,
                    medicine?.name.orEmpty(),
                    weight?.let(::formatValue).orEmpty(),
                ),
                textAlign = TextAlign.Center,
            )
            Text(
                text = result?.let { stringResource(R.string.result_volume, formatValue(it.volumeMl)) }.orEmpty(),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = result?.let { stringResource(R.string.result_dose, formatValue(it.doseMg)) }.orEmpty(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(device = WearRoundPreviewDevice, showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun RisultatoScreenPreview() {
    val viewModel = remember {
        CalculatorViewModel().apply {
            selectMedicine(medicines.first().id)
            setWeight(2.5)
        }
    }
    MaterialTheme {
        RisultatoScreen(viewModel = viewModel, onDone = {})
    }
}
