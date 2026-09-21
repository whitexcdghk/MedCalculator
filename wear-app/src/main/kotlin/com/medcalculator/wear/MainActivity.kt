package com.medcalculator.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import com.medcalculator.wear.ui.CalculatorViewModel
import com.medcalculator.wear.ui.HomeScreen
import com.medcalculator.wear.ui.ParametriScreen
import com.medcalculator.wear.ui.PesoScreen
import com.medcalculator.wear.ui.RisultatoScreen

private object Routes {
    const val HOME = "home"
    const val PARAMETRI = "parametri"
    const val PESO = "peso"
    const val RISULTATO = "risultato"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedCalculatorApp()
        }
    }
}

@Composable
private fun MedCalculatorApp() {
    MaterialTheme {
        val navController = rememberSwipeDismissableNavController()
        val viewModel: CalculatorViewModel = viewModel()

        SwipeDismissableNavHost(
            navController = navController,
            startDestination = Routes.HOME,
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    viewModel = viewModel,
                    onNext = { navController.navigate(Routes.PARAMETRI) },
                )
            }
            composable(Routes.PARAMETRI) {
                ParametriScreen(
                    viewModel = viewModel,
                    onNext = { navController.navigate(Routes.PESO) },
                )
            }
            composable(Routes.PESO) {
                PesoScreen(
                    onConfirm = { weightKg ->
                        viewModel.setWeight(weightKg)
                        navController.navigate(Routes.RISULTATO)
                    },
                )
            }
            composable(Routes.RISULTATO) {
                RisultatoScreen(
                    viewModel = viewModel,
                    onDone = {
                        navController.popBackStack(Routes.HOME, inclusive = false)
                    },
                )
            }
        }
    }
}
