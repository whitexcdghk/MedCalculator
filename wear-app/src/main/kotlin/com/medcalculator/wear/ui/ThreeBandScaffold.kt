package com.medcalculator.wear.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val TopBandHeight = 56.dp
private val BottomBandHeight = 60.dp

/**
 * The 3-band round-screen layout used by every wear-app screen in the design mockups:
 * a fixed-height top band (reserved for future use), a flexible middle band, and a
 * fixed-height bottom band holding the primary action.
 */
@Composable
fun ThreeBandScaffold(
    bottomBand: @Composable BoxScope.() -> Unit,
    topBand: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(TopBandHeight),
            contentAlignment = Alignment.Center,
            content = topBand,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            content = content,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomBandHeight),
            content = bottomBand,
        )
    }
}
