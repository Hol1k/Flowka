package com.example.flowka.viewmodels.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowka.models.Client
import com.example.flowka.repositories.client.ClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientViewModel(
    private val repository: ClientRepository
) : ViewModel() {

    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients

    init {
        loadClients()
    }

    fun addClient(name: String, phone: String, note: String){
        viewModelScope.launch {
            repository.addClient(
                Client(id = 0, name = name, phone = phone, note = note)
            )
            loadClients()
        }
    }

    private fun loadClients(){
        viewModelScope.launch {
            repository.getClients().collect { clientList ->
                _clients.value = clientList
            }
        }
    }
}