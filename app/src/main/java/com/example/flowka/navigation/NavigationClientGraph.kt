package com.example.flowka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flowka.subsystems.AddClientScreen
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
                onAddClick = { navController.navigate("add_client") }
            )
        }
        composable("add_client") {
            AddClientScreen(
                viewModel = viewModel,
                onClientAdded = { navController.popBackStack() }
            )
        }
    }
}