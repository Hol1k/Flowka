package com.example.flowka.models.dto

import com.example.flowka.models.Material
import kotlinx.serialization.Serializable

@Serializable
class MaterialDto (
    val name: String,
    val count: Int
)

fun MaterialDto.toMaterial(): Material {
    return Material(
        name = this.name,
        count = this.count
    )
}

fun Material.toDto(): MaterialDto {
    return MaterialDto(
        name = this.name,
        count = this.count
    )
}