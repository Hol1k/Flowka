package com.example.flowka.viewmodels.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowka.models.Service
import com.example.flowka.repositories.service.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ServiceViewModel(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services = _services

    init {
        loadServices()
    }

    fun addService(name: String, note: String, price: BigDecimal, duration: Int, isComplete: Boolean) {
        viewModelScope.launch {
            repository.addService(
                Service(id = 0, name = name, note = note, price = price, duration = duration, isComplete = isComplete)
            )
            loadServices()
        }
    }

    private fun loadServices() {
        viewModelScope.launch {
            repository.getServices().collect { serviceList ->
                _services.value = serviceList
            }
        }
    }
}