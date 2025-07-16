package com.example.flowka.repositories.service

import com.example.flowka.models.Service
import com.example.flowka.models.dto.toDto
import com.example.flowka.models.dto.toService
import com.example.flowka.repositories.remote.FlowkaApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteServiceRepository(
    private val api: FlowkaApi
) : ServiceRepository {
    override fun getServices(): Flow<List<Service>> = flow {
        val services = api.getServices()
        emit(services.map { it.toService() })
    }

    override suspend fun addService(service: Service) {
        api.addService(service.toDto())
    }
}