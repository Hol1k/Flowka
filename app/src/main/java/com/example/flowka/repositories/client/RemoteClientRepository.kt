package com.example.flowka.repositories.client

import com.example.flowka.models.Client
import com.example.flowka.models.dto.toClient
import com.example.flowka.models.dto.toDto
import com.example.flowka.repositories.remote.FlowkaApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteClientRepository(
    private val api: FlowkaApi
) : ClientRepository {

    override fun getClients(): Flow<List<Client>> = flow {
        val clients = api.getClients()
        emit(clients.map { it.toClient() })
    }

    override suspend fun addClient(client: Client) {
        api.addClient(client.toDto())
    }
}