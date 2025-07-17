package com.example.flowka.repositories.remote

import com.example.flowka.models.dto.ClientDto
import com.example.flowka.models.dto.MaterialDto
import com.example.flowka.models.dto.MaterialOperationDto
import com.example.flowka.models.dto.ServiceDto
import com.example.flowka.models.dto.ToolDto
import retrofit2.http.*

interface FlowkaApi {
    @GET("api/Services")
    suspend fun getServices(): List<ServiceDto>

    @POST("api/Services")
    suspend fun addService(@Body service: ServiceDto)

    @GET("api/Clients")
    suspend fun getClients(): List<ClientDto>

    @POST("api/Clients")
    suspend fun addClient(@Body client: ClientDto)

    @GET("api/Materials")
    suspend fun getMaterials(): List<MaterialDto>

    @POST("api/Materials")
    suspend fun addMaterial(@Body service: MaterialOperationDto)

    @GET("api/Tools")
    suspend fun getTools(): List<ToolDto>

    @POST("api/Tools")
    suspend fun addTool(@Body service: ToolDto)
}