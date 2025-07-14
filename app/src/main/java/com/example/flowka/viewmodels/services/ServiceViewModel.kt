package com.example.flowka.viewmodels.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowka.models.Service
import com.example.flowka.repositories.service.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ServiceViewModel(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    public val services = _services

    init {
        loadServices()
    }

    fun addService(name: String, note: String) {

    }

    private fun loadServices() {
        viewModelScope.launch {
            repository.getServices().collect { serviceList ->
                _services.value = serviceList
            }
        }
    }
}