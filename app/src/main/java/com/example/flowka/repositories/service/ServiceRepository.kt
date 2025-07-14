package com.example.flowka.repositories.service

import com.example.flowka.models.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun getServices(): Flow<List<Service>>
    fun addService(service: Service)
}