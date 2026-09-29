package com.medcalculator.wear.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.medcalculator.wear.R

@Composable
fun HomeScreen(
    viewModel: CalculatorViewModel,
    onNext: () -> Unit,
) {
    ThreeBandScaffold(
        bottomBand = {
            BandActionChip(
                label = stringResource(R.string.action_next_home),
                onClick = onNext,
                enabled = viewModel.selectedMedicineId != null,
            )
        },
    ) {
        ScalingLazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.medicines) { medicine ->
                val selected = medicine.id == viewModel.selectedMedicineId
                Chip(
                    onClick = { viewModel.selectMedicine(medicine.id) },
                    label = { Text(medicine.name) },
                    colors = if (selected) {
                        ChipDefaults.primaryChipColors()
                    } else {
                        ChipDefaults.secondaryChipColors()
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@WearPreview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(viewModel = remember { CalculatorViewModel() }, onNext = {})
    }
}
