package com.example.flowka.models.dto

import kotlinx.serialization.Serializable

@Serializable
class MaterialOperationDto (
    val id: Int,
    val materialName: String,
    val quantity: Int,
    val serviceId: Int?
)