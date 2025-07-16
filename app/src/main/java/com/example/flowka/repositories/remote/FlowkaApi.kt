package com.example.flowka.repositories.remote

import com.example.flowka.models.dto.ClientDto
import com.example.flowka.models.dto.ServiceDto
import retrofit2.http.*

interface FlowkaApi {
    @GET("api/Clients")
    suspend fun getClients(): List<ClientDto>

    @POST("api/Clients")
    suspend fun addClient(@Body client: ClientDto)

    @GET("api/Services")
    suspend fun getServices(): List<ServiceDto>

    @POST("api/Services")
    suspend fun addService(@Body service: ServiceDto)
}