package com.example.flowka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flowka.subsystems.EditClientScreen
import com.example.flowka.subsystems.ClientListScreen
import com.example.flowka.viewmodels.clients.ClientViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClientNavGraph(navController: NavHostController) {
    val viewModel: ClientViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = "client_list"
    ) {
        composable("client_list") {
            ClientListScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("edit_client") },
                onEditClick = { clientId -> navController.navigate("edit_client/${clientId}") }
            )
        }
        composable("edit_client") {
            EditClientScreen(
                viewModel = viewModel,
                clientId = -1,
                onClientAdded = { navController.popBackStack() }
            )
        }
        composable(
            "edit_client/{clientId}",
            arguments = listOf(navArgument("clientId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clientId = backStackEntry.arguments?.getInt("clientId")

            EditClientScreen(
                viewModel = viewModel,
                clientId = clientId ?: -1,
                onClientAdded = { navController.popBackStack() }
            )
        }
    }
}