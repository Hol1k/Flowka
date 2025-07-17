package com.example.flowka.models

import org.threeten.bp.LocalDate

data class Tool (
    val id: Int,
    val name: String,
    val lastMaintenanceDate: LocalDate,
    val maintenancePeriodDays: Int,
    val isActive: Boolean
)