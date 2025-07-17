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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flowka.models.Material
import com.example.flowka.viewmodels.materials.MaterialViewModel
import org.koin.androidx.compose.koinViewModel

//region MaterialListScreen

@Composable
fun MaterialListScreen(
    viewModel: MaterialViewModel = koinViewModel(),
    onAddClick: () -> Unit = {},
    onEditClick: (materialName: String) -> Unit = {}
) {
    val materials by viewModel.materials.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить метариал")
            }
        }
    ) { padding ->
        if (materials.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Пока тут нет ни одного материала")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(materials) { material ->
                    MaterialCard(material, onEditClick)
                }
            }
        }
    }
}

@Composable
fun MaterialCard(
    material: Material,
    onEditClick: (materialName: String) -> Unit = {}
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable(enabled = true) {
            onEditClick(material.name)
        }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = material.name, style = MaterialTheme.typography.titleLarge)
            Text(text = material.count.toString(), modifier = Modifier.fillMaxWidth(), style = TextStyle(textAlign = TextAlign.End))
        }
    }
}

//endregion

//region AddMaterialScreen

@Composable
fun AddMaterialScreen(
    viewModel: MaterialViewModel = koinViewModel(),
    materialName: String,
    onMaterialAdded: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var count by remember { mutableIntStateOf(0) }

    if (materialName != "") {
        val material = viewModel.materials.collectAsState().value.find { it.name == materialName }

        if (material != null) {
            name = material.name
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
            value =
                if (count <= 0)
                    ""
                else
                    count.toString()
            ,
            onValueChange = {
                val value = it.replace(Regex("[^0-9]"), "")

                count = if (value.isEmpty())
                    0
                else
                    value.toInt()
            },
            label = { Text("Количество") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.addMaterial(name, count)
                onMaterialAdded()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить")
        }
    }
}

//endregion