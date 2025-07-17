package com.example.flowka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flowka.subsystems.ToolListScreen
import com.example.flowka.subsystems.EditToolScreen
import com.example.flowka.viewmodels.tools.ToolViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ToolNavGraph(navController: NavHostController) {
    val viewModel: ToolViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = "tool_list"
    ) {
        composable("tool_list") {
            ToolListScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("edit_tool") },
                onEditClick = { toolId -> navController.navigate("edit_tool/${toolId}") }
            )
        }
        composable("edit_tool") {
            EditToolScreen(
                viewModel = viewModel,
                toolId = -1,
                onToolAdded = { navController.popBackStack() }
            )
        }
        composable(
            "edit_tool/{toolId}",
            arguments = listOf(navArgument("toolId") { type = NavType.IntType })
        ) { backStackEntry ->
            val toolId = backStackEntry.arguments?.getInt("toolId")

            EditToolScreen(
                viewModel = viewModel,
                toolId = toolId ?: -1,
                onToolAdded = { navController.popBackStack() }
            )
        }
    }
}