package com.medcalculator.wear.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.medcalculator.shared.DebugSeedData
import com.medcalculator.shared.Medicine

/**
 * Holds the state of the calculation currently in progress, shared across every screen of the
 * Home -> Parametri -> Peso -> Risultato flow.
 *
 * Reads from [DebugSeedData] for now; milestone 3 (per the design plan) replaces this with a
 * real Room-backed repository, read-only on the watch and synced from the phone.
 */
class CalculatorViewModel : ViewModel() {

    val medicines: List<Medicine> = DebugSeedData.medicines.sortedBy { it.sortOrder }

    var selectedMedicineId by mutableStateOf<String?>(null)
        private set

    var currentDosageMgPerKg by mutableStateOf<Double?>(null)
        private set

    var currentConcentrationMgPerMl by mutableStateOf<Double?>(null)
        private set

    // Kept across calculations within the same app session (not persisted across restarts yet)
    // so repeated calculations for the same animal reuse the last weight entered.
    var weightKg by mutableStateOf<Double?>(null)
        private set

    val selectedMedicine: Medicine?
        get() = medicines.find { it.id == selectedMedicineId }

    fun selectMedicine(id: String) {
        selectedMedicineId = id
        val medicine = medicines.find { it.id == id }
        currentDosageMgPerKg = medicine?.dosageMgPerKg
        currentConcentrationMgPerMl = medicine?.concentrationMgPerMl
    }

    fun setWeight(value: Double) {
        weightKg = value
    }

    /** Called when returning to Home after a calculation, ready for a new one. */
    fun resetForNewCalculation() {
        selectedMedicineId = null
        currentDosageMgPerKg = null
        currentConcentrationMgPerMl = null
    }
}
