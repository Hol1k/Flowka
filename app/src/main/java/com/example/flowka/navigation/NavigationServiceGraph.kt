package com.example.flowka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flowka.subsystems.EditServiceScreen
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
                onAddClick = { navController.navigate("edit_service") },
                onEditClick = { serviceId -> navController.navigate("edit_service/${serviceId}") }
            )
        }
        composable("edit_service") {
            EditServiceScreen(
                viewModel = viewModel,
                serviceId = -1,
                onServiceAdded = { navController.popBackStack() }
            )
        }
        composable(
            "edit_service/{serviceId}",
            arguments = listOf(navArgument("serviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getInt("serviceId")

            EditServiceScreen(
                viewModel = viewModel,
                serviceId = serviceId ?: -1,
                onServiceAdded = { navController.popBackStack() }
            )
        }
    }
}