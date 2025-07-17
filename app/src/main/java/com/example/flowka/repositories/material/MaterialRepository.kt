package com.example.flowka.repositories.material

import com.example.flowka.models.Material
import com.example.flowka.models.dto.MaterialOperationDto
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    fun getMaterials(): Flow<List<Material>>
    suspend fun addMaterial(materialOperation: MaterialOperationDto)
}