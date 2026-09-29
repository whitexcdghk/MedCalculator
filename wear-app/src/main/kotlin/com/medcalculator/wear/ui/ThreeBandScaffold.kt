package com.medcalculator.wear.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text

// ---- Visual knobs: tweak these and hit Build & Refresh in the preview. ----

/** Height of the top band (reserved, currently empty on every screen). */
private val TopBandHeight = 40.dp

/** Height of the bottom band (the primary action button). */
private val BottomBandHeight = 40.dp

/** Gap between a band's content and the middle band. */
private val BandInnerPadding = 4.dp

/** Gap between a band's content and the screen edge (the curve, on a round watch). */
private val EdgeMargin = 4.dp

/** Rounding of the band content's corners on the side facing the middle band. */
private val BandCornerRadius = 16.dp

/**
 * The 3-band round-screen layout used by every wear-app screen in the design mockups:
 * a fixed-height top band (reserved for future use), a flexible middle band, and a
 * fixed-height bottom band holding the primary action.
 *
 * The top and bottom bands' content is clipped to an "edge" shape: rounded corners on the
 * side facing the middle band, and on a round screen the outer side follows the screen's
 * curve (like Wear OS's edge button). Change the knobs above and the shape follows.
 */
@Composable
fun ThreeBandScaffold(
    bottomBand: @Composable BoxScope.() -> Unit,
    topBand: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val isRound = LocalConfiguration.current.isScreenRound
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screen = Size(maxWidth.value, maxHeight.value)

        Column(modifier = Modifier.fillMaxSize()) {
            EdgeBand(TopBandHeight, atTop = true, screen, isRound, topBand)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                content = content,
            )
            EdgeBand(BottomBandHeight, atTop = false, screen, isRound, bottomBand)
        }
    }
}

/** A top or bottom band whose content slot is clipped to [EdgeBandShape]. */
@Composable
private fun EdgeBand(
    bandHeight: Dp,
    atTop: Boolean,
    screenDp: Size,
    isRound: Boolean,
    content: @Composable BoxScope.() -> Unit,
) {
    // On a round screen the circle itself keeps content off the outer edge; on a square one
    // the outer side needs the margin as plain padding.
    val outerPadding = if (isRound) 0.dp else EdgeMargin
    val slotPadding = if (atTop) {
        Modifier.padding(start = EdgeMargin, end = EdgeMargin, top = outerPadding, bottom = BandInnerPadding)
    } else {
        Modifier.padding(start = EdgeMargin, end = EdgeMargin, top = BandInnerPadding, bottom = outerPadding)
    }
    // Slot's top-left corner in screen coordinates (dp), so the shape can place the circle.
    val slotOrigin = Offset(
        x = EdgeMargin.value,
        y = if (atTop) outerPadding.value else screenDp.height - bandHeight.value + BandInnerPadding.value,
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bandHeight),
    ) {
        Box(
            modifier = slotPadding
                .fillMaxSize()
                .clip(EdgeBandShape(atTop, slotOrigin, screenDp, isRound)),
            contentAlignment = Alignment.Center,
            content = content,
        )
    }
}

/**
 * Rounded corners ([BandCornerRadius]) on the side facing the middle band; on a round screen,
 * intersected with the screen circle shrunk by [EdgeMargin] so the outer side follows the curve.
 */
private class EdgeBandShape(
    private val atTop: Boolean,
    private val slotOriginDp: Offset,
    private val screenDp: Size,
    private val isRound: Boolean,
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val corner = CornerRadius(with(density) { BandCornerRadius.toPx() })
        val inner = if (atTop) {
            RoundRect(Rect(Offset.Zero, size), bottomLeft = corner, bottomRight = corner)
        } else {
            RoundRect(Rect(Offset.Zero, size), topLeft = corner, topRight = corner)
        }
        val rect = Path().apply { addRoundRect(inner) }
        if (!isRound) return Outline.Generic(rect)

        val px = density.density
        val circleCenter = Offset(
            x = (screenDp.width / 2 - slotOriginDp.x) * px,
            y = (screenDp.height / 2 - slotOriginDp.y) * px,
        )
        val circleRadius = (minOf(screenDp.width, screenDp.height) / 2 - EdgeMargin.value) * px
        val circle = Path().apply { addOval(Rect(circleCenter, circleRadius)) }
        return Outline.Generic(Path.combine(PathOperation.Intersect, rect, circle))
    }
}

/** The primary-action button for a bottom band: fills its slot (the band clips its shape). */
@Composable
fun BandActionChip(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Chip(
        onClick = onClick,
        enabled = enabled,
        label = {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        colors = ChipDefaults.primaryChipColors(),
        shape = RectangleShape,
        modifier = Modifier.fillMaxSize(),
    )
}
