package com.example.flowka.models.dto

import com.example.flowka.models.Client
import kotlinx.serialization.Serializable

@Serializable
class ClientDto (
    val id: Int,
    val name: String,
    val phone: String,
    val note: String
)

fun ClientDto.toClient(): Client {
    return Client(
        id = this.id,
        name = this.name,
        phone = this.phone,
        note = this.note
    )
}

fun Client.toDto(): ClientDto {
    return ClientDto(
        id = this.id,
        name = this.name,
        phone = this.phone,
        note = this.note
    )
}