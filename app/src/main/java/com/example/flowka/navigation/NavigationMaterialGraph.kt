package com.example.flowka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flowka.subsystems.AddMaterialScreen
import com.example.flowka.subsystems.MaterialListScreen
import com.example.flowka.viewmodels.materials.MaterialViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MaterialNavGraph(navController: NavHostController) {
    val viewModel: MaterialViewModel = koinViewModel()
    viewModel.loadMaterials()

    NavHost(
        navController = navController,
        startDestination = "material_list"
    ) {
        composable("material_list") {
            MaterialListScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("edit_material") },
                onEditClick = { materialName -> navController.navigate("edit_material/${materialName}") }
            )
        }
        composable("edit_material") {
            AddMaterialScreen(
                viewModel = viewModel,
                materialName = "",
                onMaterialAdded = { navController.popBackStack() }
            )
        }
        composable(
            "edit_material/{materialName}",
            arguments = listOf(navArgument("materialName") { type = NavType.StringType })
        ) { backStackEntry ->
            val materialName = backStackEntry.arguments?.getString("materialName")

            AddMaterialScreen(
                viewModel = viewModel,
                materialName = materialName ?: "",
                onMaterialAdded = { navController.popBackStack() }
            )
        }
    }
}