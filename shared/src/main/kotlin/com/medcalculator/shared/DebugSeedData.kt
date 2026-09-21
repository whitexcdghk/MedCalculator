package com.medcalculator.shared

/**
 * Placeholder catalog for development and UI testing only.
 *
 * These are NOT real drugs, dosages or concentrations — do not use for any clinical decision.
 * The real catalog will be entered by the user (via the phone companion app) once available.
 */
object DebugSeedData {

    val medicines: List<Medicine> = listOf(
        Medicine(
            id = "debug-farmaco-a",
            name = "Farmaco Test A",
            species = listOf("dog"),
            concentrationMgPerMl = 50.0,
            concentrationPresetsMgPerMl = listOf(50.0, 25.0, 100.0),
            dosageMgPerKg = 5.0,
            dosagePresetsMgPerKg = listOf(5.0, 2.5, 10.0),
            sortOrder = 0,
        ),
        Medicine(
            id = "debug-farmaco-b",
            name = "Farmaco Test B",
            species = listOf("cat"),
            concentrationMgPerMl = 100.0,
            concentrationPresetsMgPerMl = listOf(100.0, 50.0),
            dosageMgPerKg = 3.0,
            dosagePresetsMgPerKg = listOf(2.0, 3.0, 4.0),
            sortOrder = 1,
        ),
        Medicine(
            id = "debug-farmaco-c",
            name = "Farmaco Test C",
            species = listOf("dog", "cat"),
            concentrationMgPerMl = 20.0,
            concentrationPresetsMgPerMl = listOf(20.0),
            dosageMgPerKg = 10.0,
            dosagePresetsMgPerKg = listOf(10.0),
            sortOrder = 2,
        ),
    )
}
