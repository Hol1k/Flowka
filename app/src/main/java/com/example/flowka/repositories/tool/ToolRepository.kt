package com.example.flowka.repositories.tool

import com.example.flowka.models.Tool
import kotlinx.coroutines.flow.Flow

interface ToolRepository {
    fun getTools(): Flow<List<Tool>>
    suspend fun addTool(tool: Tool)
}