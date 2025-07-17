package com.example.flowka.repositories.tool

import com.example.flowka.models.Tool
import com.example.flowka.models.dto.toDto
import com.example.flowka.models.dto.toTool
import com.example.flowka.repositories.remote.FlowkaApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteToolRepository(
    private val api: FlowkaApi
) : ToolRepository {

    override fun getTools(): Flow<List<Tool>> = flow {
        val tools = api.getTools()
        emit(tools.map { it.toTool() })
    }

    override suspend fun addTool(tool: Tool) {
        api.addTool(tool.toDto())
    }
}