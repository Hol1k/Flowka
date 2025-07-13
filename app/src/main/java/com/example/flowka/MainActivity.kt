package com.example.flowka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.compose.rememberNavController
import com.example.flowka.navigation.ClientNavGraph
import com.example.flowka.subsystems.FinanceScreen
import com.example.flowka.subsystems.MaterialsScreen
import com.example.flowka.subsystems.ServicesScreen
import com.example.flowka.subsystems.ToolsScreen
import com.example.flowka.ui.theme.FlowkaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowkaTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                var currentScreen by remember { mutableStateOf("Услуги") }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Home,
                                        contentDescription = "Услуги"
                                    )
                                },
                                shape = RectangleShape,
                                label = { Text("Услуги") },
                                selected = currentScreen == "Услуги",
                                onClick = {
                                    currentScreen = "Услуги"
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = "Клиенты"
                                    )
                                },
                                shape = RectangleShape,
                                label = { Text("Клиенты") },
                                selected = currentScreen == "Клиенты",
                                onClick = {
                                    currentScreen = "Клиенты"
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        Icons.Default.ShoppingCart,
                                        contentDescription = "Материалы"
                                    )
                                },
                                shape = RectangleShape,
                                label = { Text("Материалы") },
                                selected = currentScreen == "Материалы",
                                onClick = {
                                    currentScreen = "Материалы"
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Build,
                                        contentDescription = "Инструменты"
                                    )
                                },
                                shape = RectangleShape,
                                label = { Text("Инструменты") },
                                selected = currentScreen == "Инструменты",
                                onClick = {
                                    currentScreen = "Инструменты"
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = "Финансы"
                                    )
                                },
                                shape = RectangleShape,
                                label = { Text("Финансы") },
                                selected = currentScreen == "Финансы",
                                onClick = {
                                    currentScreen = "Финансы"
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = { Text(currentScreen) },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open() }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Меню")
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            when (currentScreen) {
                                "Услуги" -> ServicesScreen()
                                "Клиенты" -> {
                                    val navController = rememberNavController()
                                    ClientNavGraph(navController)
                                }
                                "Материалы" -> MaterialsScreen()
                                "Инструменты" -> ToolsScreen()
                                "Финансы" -> FinanceScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}