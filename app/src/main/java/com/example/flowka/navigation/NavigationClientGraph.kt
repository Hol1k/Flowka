package com.example.flowka.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flowka.subsystems.AddClientScreen
import com.example.flowka.subsystems.ClientListScreen
import com.example.flowka.viewmodels.clients.ClientViewModel

@Composable
fun ClientNavGraph(navController: NavHostController) {
    val viewModel: ClientViewModel = viewModel()

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