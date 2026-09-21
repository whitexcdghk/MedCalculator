package com.medcalculator.shared

data class DoseResult(
    val doseMg: Double,
    val volumeMl: Double,
)

object DoseCalculator {

    /**
     * doseMg = weightKg * dosageMgPerKg
     * volumeMl = doseMg / concentrationMgPerMl
     */
    fun calculate(
        weightKg: Double,
        dosageMgPerKg: Double,
        concentrationMgPerMl: Double,
    ): DoseResult {
        require(weightKg >= 0) { "weightKg must not be negative" }
        require(dosageMgPerKg >= 0) { "dosageMgPerKg must not be negative" }
        require(concentrationMgPerMl > 0) { "concentrationMgPerMl must be greater than zero" }

        val doseMg = weightKg * dosageMgPerKg
        val volumeMl = doseMg / concentrationMgPerMl
        return DoseResult(doseMg = doseMg, volumeMl = volumeMl)
    }
}
