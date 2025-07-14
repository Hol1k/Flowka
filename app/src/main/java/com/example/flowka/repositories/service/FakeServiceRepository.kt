package com.example.flowka.repositories.service

import com.example.flowka.models.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeServiceRepository : ServiceRepository {
    private val services = MutableStateFlow(
        listOf(
            Service(1, "Услуга1", "коммент"),
            Service(2, "Услуга2", "комменткомменткомменткомменткомменткомменткоммент"),
            Service(3, "Услуга3", "коммент")
        )
    )

    override fun getServices(): Flow<List<Service>> {
        return services
    }

    override fun addService(service: Service) {
        val current = services.value.toMutableList()
        current.add(service)
        services.value = current
    }
}