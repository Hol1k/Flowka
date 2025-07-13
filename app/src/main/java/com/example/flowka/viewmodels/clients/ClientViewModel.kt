package com.example.flowka.viewmodels.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowka.models.Client
import com.example.flowka.repositories.client.ClientRepository
import com.example.flowka.repositories.client.FakeClientRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClientViewModel : ViewModel() {
    private val repository: ClientRepository = FakeClientRepository()

    val clients: StateFlow<List<Client>> = repository.getClients()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addClient(name: String, phone: String, note: String){
        viewModelScope.launch {
            repository.addClient(
                Client(id = 0, name = name, phone = phone, note = note)
            )
        }
    }
}