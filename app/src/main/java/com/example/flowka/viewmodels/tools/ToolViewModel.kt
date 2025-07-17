package com.example.flowka.viewmodels.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowka.models.Tool
import com.example.flowka.repositories.tool.ToolRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class ToolViewModel(
    private val repository: ToolRepository
) : ViewModel() {

    private val _tools = MutableStateFlow<List<Tool>>(emptyList())
    val tools = _tools

    init {
        loadTools()
    }

    fun addTool(name: String, lastMaintenanceDate: LocalDate, maintenancePeriodDays: Int, isActive: Boolean) {
        viewModelScope.launch {
            repository.addTool(
                Tool(id = 0, name = name, lastMaintenanceDate = lastMaintenanceDate, maintenancePeriodDays = maintenancePeriodDays, isActive = isActive)
            )
            loadTools()
        }
    }

    private fun loadTools() {
        viewModelScope.launch {
            repository.getTools().collect { toolList ->
                _tools.value = toolList
            }
        }
    }
}