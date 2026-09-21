package com.medcalculator.shared

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class DoseCalculatorTest {

    @Test
    fun `standard values produce expected dose and volume`() {
        // Farmaco 2 example from the design mockups: 5 mg/kg, 50 mg/mL, 2.5 kg -> 12.5 mg, 0.25 mL
        val result = DoseCalculator.calculate(
            weightKg = 2.5,
            dosageMgPerKg = 5.0,
            concentrationMgPerMl = 50.0,
        )

        assertEquals(12.5, result.doseMg, 1e-9)
        assertEquals(0.25, result.volumeMl, 1e-9)
    }

    @Test
    fun `preset dosage value is used as-is`() {
        val result = DoseCalculator.calculate(
            weightKg = 4.0,
            dosageMgPerKg = 10.0,
            concentrationMgPerMl = 20.0,
        )

        assertEquals(40.0, result.doseMg, 1e-9)
        assertEquals(2.0, result.volumeMl, 1e-9)
    }

    @Test
    fun `zero weight yields zero dose and volume`() {
        val result = DoseCalculator.calculate(
            weightKg = 0.0,
            dosageMgPerKg = 5.0,
            concentrationMgPerMl = 50.0,
        )

        assertEquals(0.0, result.doseMg, 1e-9)
        assertEquals(0.0, result.volumeMl, 1e-9)
    }

    @Test
    fun `zero concentration is rejected`() {
        assertThrows(IllegalArgumentException::class.java) {
            DoseCalculator.calculate(
                weightKg = 2.5,
                dosageMgPerKg = 5.0,
                concentrationMgPerMl = 0.0,
            )
        }
    }

    @Test
    fun `negative weight is rejected`() {
        assertThrows(IllegalArgumentException::class.java) {
            DoseCalculator.calculate(
                weightKg = -1.0,
                dosageMgPerKg = 5.0,
                concentrationMgPerMl = 50.0,
            )
        }
    }
}
