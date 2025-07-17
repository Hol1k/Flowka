package com.example.flowka.models

import com.example.flowka.models.dto.MaterialOperationDto
import java.math.BigDecimal

data class Service (
    val id: Int,
    val name: String,
    val note: String,
    val price: BigDecimal,
    val duration: Int,
    val isComplete: Boolean,
    val clientId: Int,
    val materialOperations: List<MaterialOperationDto>,
    val tools: List<Tool>
)