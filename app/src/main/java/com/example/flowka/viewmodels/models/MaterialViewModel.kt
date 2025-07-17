package com.example.flowka.viewmodels.materials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowka.models.Material
import com.example.flowka.models.dto.MaterialOperationDto
import com.example.flowka.repositories.material.MaterialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MaterialViewModel(
    private val repository: MaterialRepository
) : ViewModel() {

    private val _materials = MutableStateFlow<List<Material>>(emptyList())
    val materials: StateFlow<List<Material>> = _materials

    init {
        loadMaterials()
    }

    fun addMaterial(materialName: String, quantity: Int){
        viewModelScope.launch {
            repository.addMaterial(
                MaterialOperationDto(id = 0, materialName = materialName, quantity = quantity, serviceId = null)
            )
            loadMaterials()
        }
    }

    private fun loadMaterials(){
        viewModelScope.launch {
            repository.getMaterials().collect { materialList ->
                _materials.value = materialList
            }
        }
    }
}