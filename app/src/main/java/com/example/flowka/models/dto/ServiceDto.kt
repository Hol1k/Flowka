package com.example.flowka.models.dto

import com.example.flowka.models.Service
import kotlinx.serialization.Serializable

@Serializable
class ServiceDto (
    val id: Int,
    val name: String,
    val note: String,
    val price: String,
    val duration: Int,
    val isComplete: Boolean,
    val clientId: Int
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
        clientId = this.clientId
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
        clientId = this.clientId
    )
}