package com.example.flowka.repositories.service

import com.example.flowka.models.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeServiceRepository : ServiceRepository {
    private val services = MutableStateFlow(
        listOf(
            Service(1, "Услуга1", "коммент", 0.toBigDecimal(), 30, false),
            Service(2, "Услуга2", "комменткомменткомменткомменткомменткомменткоммент", 0.toBigDecimal(), 30, false),
            Service(3, "Услуга3", "коммент", 0.toBigDecimal(), 30, false)
        )
    )

    override fun getServices(): Flow<List<Service>> {
        return services
    }

    override suspend fun addService(service: Service) {
        val current = services.value.toMutableList()
        current.add(service)
        services.value = current
    }
}