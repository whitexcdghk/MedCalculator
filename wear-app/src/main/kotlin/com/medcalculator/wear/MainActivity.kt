package com.medcalculator.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.medcalculator.shared.DebugSeedData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearAppPlaceholder()
        }
    }
}

@Composable
private fun WearAppPlaceholder() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            // Proves the shared module is wired up correctly; the real Home screen
            // (milestone 2) will read this list through Room instead.
            val medicineCount = DebugSeedData.medicines.size
            Text(
                text = "${stringResource(R.string.placeholder_message)}\n($medicineCount)",
                textAlign = TextAlign.Center,
            )
        }
    }
}
