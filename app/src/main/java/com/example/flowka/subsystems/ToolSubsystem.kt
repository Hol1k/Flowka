package com.example.flowka.subsystems

import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.flowka.models.Tool
import com.example.flowka.viewmodels.tools.ToolViewModel
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

//region ToolListScreen

@Composable
fun ToolListScreen(
    viewModel: ToolViewModel = koinViewModel(),
    onAddClick: () -> Unit = {},
    onEditClick: (toolId: Int) -> Unit = {}
) {
    val tools by viewModel.tools.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить инструмент")
            }
        }
    ) { padding ->
        if (tools.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Пока тут нет ни одного инструмента")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(tools) { tool ->
                    ToolCard(tool, onEditClick)
                }
            }
        }
    }
}

@Composable
fun ToolCard(
    tool: Tool,
    onEditClick: (toolId: Int) -> Unit = {}
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable(enabled = true) {
            onEditClick(tool.id)
        }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = tool.name, style = MaterialTheme.typography.titleLarge)
        }
    }
}

//endregion

//region EditToolScreen

@Composable
fun EditToolScreen(
    viewModel: ToolViewModel = koinViewModel(),
    toolId: Int,
    onToolAdded: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var lastMaintenanceDate by remember { mutableStateOf(LocalDate.now()) }
    var maintenancePeriodDays by remember { mutableIntStateOf(0) }
    var isActive by remember { mutableStateOf(false) }

    if (toolId != -1) {
        val tool = viewModel.tools.collectAsState().value.find { it.id == toolId }

        if (tool != null) {
            name = tool.name
            lastMaintenanceDate = tool.lastMaintenanceDate
            maintenancePeriodDays = tool.maintenancePeriodDays
            isActive = tool.isActive
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        val context = LocalContext.current
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        Column {
            Text("Последнее обслуживание", style = MaterialTheme.typography.labelMedium)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                    .clickable {
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                lastMaintenanceDate = LocalDate.of(year, month + 1, day)
                            },
                            lastMaintenanceDate.year,
                            lastMaintenanceDate.monthValue - 1,
                            lastMaintenanceDate.dayOfMonth
                        ).show()

                        isActive = lastMaintenanceDate.plusDays(maintenancePeriodDays.toLong()) > LocalDate.now()
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = lastMaintenanceDate.format(dateFormatter),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value =
                if (maintenancePeriodDays == 0)
                    ""
                else
                    maintenancePeriodDays.toString()
            ,
            onValueChange = {
                val value = it.replace(Regex("[^0-9]"), "")

                maintenancePeriodDays = if (value.isEmpty())
                    0
                else
                    value.toInt()

                isActive = lastMaintenanceDate.plusDays(maintenancePeriodDays.toLong()) > LocalDate.now()
            },
            label = { Text("Частота обслуживания (дней)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        )
        {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Активен")
                Checkbox(
                    checked =
                        isActive,
                    onCheckedChange = {
                        isActive = it
                    },
                    enabled = false
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.addTool(name, lastMaintenanceDate = lastMaintenanceDate, maintenancePeriodDays = maintenancePeriodDays, isActive = isActive)
                onToolAdded()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = toolId == -1
        ) {
            Text("Сохранить")
        }
    }
}

//endregion