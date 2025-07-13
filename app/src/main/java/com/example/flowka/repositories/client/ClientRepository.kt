package com.example.flowka.repositories.client

import com.example.flowka.models.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun getClients(): Flow<List<Client>>
    suspend fun addClient(client: Client)
} 