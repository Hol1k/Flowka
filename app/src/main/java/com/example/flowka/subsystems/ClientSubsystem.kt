package com.example.flowka.subsystems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flowka.models.Client
import com.example.flowka.viewmodels.clients.ClientViewModel
import org.koin.androidx.compose.koinViewModel

//region ClientListScreen

@Composable
fun ClientListScreen(
    viewModel: ClientViewModel = koinViewModel(),
    onAddClick: () -> Unit = {},
    onEditClick: (clientId: Int) -> Unit = {}
) {
    val clients by viewModel.clients.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить клиента")
            }
        }
    ) { padding ->
        if (clients.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Пока тут нет ни одного клиента")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(clients) { client ->
                    ClientCard(client, onEditClick)
                }
            }
        }
    }
}

@Composable
fun ClientCard(
    client: Client,
    onEditClick: (clientId: Int) -> Unit = {}
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable(enabled = true) {
            onEditClick(client.id)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = client.name, style = MaterialTheme.typography.titleLarge)
            Text(text = client.phone, style = MaterialTheme.typography.bodyMedium)
            Text(text = client.note, style = MaterialTheme.typography.bodySmall)
        }
    }
}

//endregion

//region EditClientScreen

@Composable
fun EditClientScreen(
    viewModel: ClientViewModel = koinViewModel(),
    clientId: Int,
    onClientAdded: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    if (clientId != -1) {
        val client = viewModel.clients.collectAsState().value.find { it.id == clientId }

        if (client != null) {
            name = client.name
            phoneNumber = client.phone
            note = client.note
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
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Номер") },
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

        Button(
            onClick = {
                viewModel.addClient(name, phoneNumber, note)
                onClientAdded()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = clientId == -1
        ) {
            Text("Сохранит")
        }
    }
}

//endregion