package com.example.flowka.di

import com.example.flowka.repositories.client.ClientRepository
import com.example.flowka.repositories.material.MaterialRepository
import com.example.flowka.repositories.service.ServiceRepository
import com.example.flowka.repositories.tool.ToolRepository
import com.example.flowka.viewmodels.clients.ClientViewModel
import com.example.flowka.viewmodels.materials.MaterialViewModel
import com.example.flowka.viewmodels.services.ServiceViewModel
import com.example.flowka.viewmodels.tools.ToolViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ClientViewModel(get<ClientRepository>()) }
    viewModel { ServiceViewModel(get<ServiceRepository>()) }
    viewModel { MaterialViewModel(get<MaterialRepository>()) }
    viewModel { ToolViewModel(get<ToolRepository>()) }
}