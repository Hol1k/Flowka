package com.example.flowka.models

import java.math.BigDecimal

data class Service (
    val id: Int,
    val name: String,
    val note: String,
    val price: BigDecimal,
    val duration: Int,
    val isComplete: Boolean
)