package com.example.flowka.repositories.material

import com.example.flowka.models.Material
import com.example.flowka.models.dto.MaterialOperationDto
import com.example.flowka.models.dto.toMaterial
import com.example.flowka.repositories.remote.FlowkaApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteMaterialRepository(
    private val api: FlowkaApi
) : MaterialRepository {

    override fun getMaterials(): Flow<List<Material>> = flow {
        val materials = api.getMaterials()
        emit(materials.map { it.toMaterial() })
    }

    override suspend fun addMaterial(materialOperation: MaterialOperationDto) {
        api.addMaterial(materialOperation)
    }
}