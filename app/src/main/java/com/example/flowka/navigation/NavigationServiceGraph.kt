package com.example.flowka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flowka.subsystems.AddServiceScreen
import com.example.flowka.subsystems.ServiceListScreen
import com.example.flowka.viewmodels.services.ServiceViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceNavGraph(navController: NavHostController) {
    val viewModel: ServiceViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = "service_list"
    ) {
        composable("service_list") {
            ServiceListScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("add_service") }
            )
        }

        composable("add_service") {
            AddServiceScreen(
                viewModel = viewModel,
                onServiceAdded = { navController.popBackStack() }
            )
        }
    }
}