package com.example.flowka.subsystems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.flowka.models.Service
import com.example.flowka.models.dto.MaterialOperationDto
import com.example.flowka.viewmodels.clients.ClientViewModel
import com.example.flowka.viewmodels.materials.MaterialViewModel
import com.example.flowka.viewmodels.services.ServiceViewModel
import com.example.flowka.viewmodels.tools.ToolViewModel
import org.koin.androidx.compose.koinViewModel

//region ServiceListScreen

@Composable
fun ServiceListScreen(
    viewModel: ServiceViewModel = koinViewModel(),
    onAddClick: () -> Unit = {},
    onEditClick: (serviceId: Int) -> Unit = {}
) {
    val services by viewModel.services.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить запись")
            }
        }
    ) { padding ->
        if (services.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Пока тут нет ни одной услуги")
            }
        } else {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(padding)
            ) {
                items(services) { service ->
                    ServiceCard(service, onEditClick)
                }
            }
        }
    }
}

@Composable
fun ServiceCard(
    service: Service,
    onEditClick: (serviceId: Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(enabled = true) {
                onEditClick(service.id)
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = service.name, style = MaterialTheme.typography.titleLarge)
            Text(text = service.note, style = MaterialTheme.typography.bodySmall)
        }
    }
}

//endregion

//region EditServiceScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServiceScreen(
    viewModel: ServiceViewModel = koinViewModel(),
    clientViewModel: ClientViewModel = koinViewModel(),
    toolViewModel: ToolViewModel = koinViewModel(),
    materialViewModel: MaterialViewModel = koinViewModel(),
    serviceId: Int,
    onServiceAdded: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(0.toBigDecimal()) }
    var duration by remember { mutableIntStateOf(0) }
    var isComplete by remember { mutableStateOf(false) }

    if (serviceId != -1) {
        val service = viewModel.services.collectAsState().value.find { it.id == serviceId }

        if (service != null) {
            name = service.name
            note = service.note
            price = service.price
            duration = service.duration
            isComplete = service.isComplete
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

        val clients = clientViewModel.clients.collectAsState()
        var expanded by remember { mutableStateOf(false) }
        var selectedClientId by remember { mutableStateOf<Int?>(null) }

        var selectedClientName = clients.value.find { it.id == selectedClientId }?.name ?: "Выберите клиента"

        if (serviceId != -1) {
            val service = viewModel.services.collectAsState().value.find { it.id == serviceId }

            if (service != null) {
                selectedClientName = clients.value.find { it.id == service.clientId }?.name ?: "Выберите клиента"
                selectedClientId = service.clientId
            }
        }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedClientName,
                onValueChange = {},
                label = { Text("Клиент") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                clients.value.forEach { client ->
                    DropdownMenuItem(
                        text = { Text(client.name) },
                        onClick = {
                            selectedClientId = client.id
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Заметка") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value =
                if (price.compareTo(0.toBigDecimal()) == 0)
                    ""
                else
                    price.toString()
            ,
            onValueChange = {
                price = if (it.isEmpty())
                    0.toBigDecimal()
                else
                    it.toBigDecimal()
                            },
            label = { Text("Цена") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value =
                if (duration == 0)
                    ""
                else
                    duration.toString()
            ,
            onValueChange = {
                val value = it.replace(Regex("[^0-9]"), "")

                duration = if (value.isEmpty())
                    0
                else
                    value.toInt()
            },
            label = { Text("Длительность (минут)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        val allTools = toolViewModel.tools.collectAsState().value
        val selectedToolIds = remember { mutableStateListOf<Int>() }

        if (serviceId != -1) {
            val service = viewModel.services.collectAsState().value.find { it.id == serviceId }
            if (service != null) {
                selectedToolIds.clear()
                selectedToolIds.addAll(service.tools.map { it.id })
            }
        }

        Text("Инструменты", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(allTools) { tool ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedToolIds.contains(tool.id),
                        onCheckedChange = { isChecked ->
                            if (isChecked) selectedToolIds.add(tool.id)
                            else selectedToolIds.remove(tool.id)
                        }
                    )
                    Text(tool.name)
                }
            }
        }

        val allMaterials = materialViewModel.materials.collectAsState().value
        val materialCounts = remember { mutableStateMapOf<String, Int>() }

        Text("Материалы", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(allMaterials) { material ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(
                        checked = materialCounts.containsKey(material.name),
                        onCheckedChange = { isChecked ->
                            if (isChecked) materialCounts[material.name] = 1
                            else materialCounts.remove(material.name)
                        }
                    )
                    Text(material.name, modifier = Modifier.weight(1f))
                    if (materialCounts.containsKey(material.name)) {
                        OutlinedTextField(
                            value = materialCounts[material.name]?.toString() ?: "",
                            onValueChange = {
                                val sanitized = it.filter { c -> c.isDigit() }
                                materialCounts[material.name] = sanitized.toIntOrNull() ?: 1
                            },
                            label = { Text("Кол-во") },
                            modifier = Modifier.width(100.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        )
        {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Выполнена")
                Checkbox(
                    checked =
                        isComplete,
                    onCheckedChange = {
                        isComplete = it
                    },
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val selectedTools = allTools.filter { selectedToolIds.contains(it.id) }

                val selectedMaterials = allMaterials.mapNotNull { mat ->
                    materialCounts[mat.name]?.let { count ->
                        MaterialOperationDto(id = 0, materialName = mat.name, quantity = -count, serviceId = serviceId)
                    }
                }

                viewModel.addService(name, note, price, duration, isComplete, selectedClientId ?: -1, selectedMaterials, selectedTools)
                onServiceAdded()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = serviceId == -1 && selectedClientId != null
        ) {
            Text("Сохранить")
        }
    }
}

//endregion