package com.example.flowka.repositories.client

import com.example.flowka.models.Client
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeClientRepository : ClientRepository {
    private val clients = MutableStateFlow(
        listOf(
            Client(1, "Иван Иванов", "+7 999 123-45-67", "Постоянный клиент"),
            Client(2, "Мария Смирнова", "+7 999 987-65-43", "Скидка 10%"),
            Client(3, "Алексей Петров", "+7 999 111-22-33", "Просил перезвонить позже")
        )
    )

    override fun getClients(): Flow<List<Client>> = clients.asStateFlow()

    override suspend fun addClient(client: Client) {
        val current = clients.value.toMutableList()
        current.add(client.copy(id = current.size + 1))
        clients.value = current
    }
}