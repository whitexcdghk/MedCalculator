package com.medcalculator.wear.ui

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * Preview used by every screen in this module: Android Studio's built-in small round Wear OS
 * device (~192dp across, close to the Galaxy Watch FE 40mm). `showSystemUi` is what makes
 * Studio clip the preview to the round screen shape instead of a square canvas.
 */
@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF000000,
)
internal annotation class WearPreview
