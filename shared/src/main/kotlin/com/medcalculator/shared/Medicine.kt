package com.medcalculator.shared

/**
 * A medicine entry in the catalog. Dosage and concentration are "semi-standard": prefilled
 * with the values below but editable at calculation time via presets or a custom value
 * (see the "Parametri farmaco" screen in the design) — an edit made there is never written
 * back here; only the phone companion app can change the catalog itself.
 */
data class Medicine(
    val id: String,
    val name: String,
    val species: List<String>,
    val concentrationMgPerMl: Double,
    val concentrationPresetsMgPerMl: List<Double>,
    val dosageMgPerKg: Double,
    val dosagePresetsMgPerKg: List<Double>,
    val notes: String? = null,
    val sortOrder: Int,
)
