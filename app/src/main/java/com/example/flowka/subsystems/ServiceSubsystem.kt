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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.flowka.models.Service
import com.example.flowka.viewmodels.services.ServiceViewModel
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

@Composable
fun EditServiceScreen(
    viewModel: ServiceViewModel = koinViewModel(),
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
                viewModel.addService(name, note, price, duration, isComplete)
                onServiceAdded()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = serviceId == -1
        ) {
            Text("Сохранить")
        }
    }
}

//endregion