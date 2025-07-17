package com.example.flowka.models.dto

import com.example.flowka.models.Service
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ServiceDto (
    val id: Int,
    val name: String,
    val note: String,
    val price: String,
    val duration: Int,
    val isComplete: Boolean,
    val clientId: Int,
    @SerialName("materialOperations")
    val materialOperationDtos: List<MaterialOperationDto> = emptyList(),
    @SerialName("tools")
    val toolDtos: List<ToolDto> = emptyList()
)

fun ServiceDto.toService(): Service
{
    return Service(
        id = this.id,
        name = this.name,
        note = this.note,
        price = this.price.toBigDecimal(),
        duration = this.duration,
        isComplete = this.isComplete,
        clientId = this.clientId,
        materialOperations = this.materialOperationDtos,
        tools = this.toolDtos.map { it.toTool() }
    )
}

fun Service.toDto(): ServiceDto
{
    return ServiceDto(
        id = this.id,
        name = this.name,
        note = this.note,
        price = this.price.toString(),
        duration = this.duration,
        isComplete = this.isComplete,
        clientId = this.clientId,
        materialOperationDtos = this.materialOperations,
        toolDtos = this.tools.map { it.toDto() }
    )
}