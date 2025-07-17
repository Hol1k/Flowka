package com.example.flowka.models.dto

import com.example.flowka.models.Tool
import com.example.flowka.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import org.threeten.bp.LocalDate


@Serializable
class ToolDto (
    val id: Int,
    val name: String,
    @Serializable(with = LocalDateSerializer::class)
    val lastMaintenanceDate: LocalDate,
    val maintenancePeriodDays: Int,
    val isActive: Boolean
)

fun ToolDto.toTool(): Tool {
    return Tool(
        id = this.id,
        name = this.name,
        lastMaintenanceDate = this.lastMaintenanceDate,
        maintenancePeriodDays = this.maintenancePeriodDays,
        isActive = this.isActive
    )
}

fun Tool.toDto(): ToolDto {
    return ToolDto(
        id = this.id,
        name = this.name,
        lastMaintenanceDate = this.lastMaintenanceDate,
        maintenancePeriodDays = this.maintenancePeriodDays,
        isActive = this.isActive
    )
}