package com.example.flowka.repositories.client.remote

import com.example.flowka.models.dto.ClientDto
import retrofit2.http.*

interface FlowkaApi {
    @GET("api/Clients")
    suspend fun getClients(): List<ClientDto>

    @POST("api/Clients")
    suspend fun addClient(@Body client: ClientDto)
}