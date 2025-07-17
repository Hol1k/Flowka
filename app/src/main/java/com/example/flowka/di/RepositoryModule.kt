package com.example.flowka.di

import com.example.flowka.repositories.client.ClientRepository
import com.example.flowka.repositories.client.RemoteClientRepository
import com.example.flowka.repositories.material.MaterialRepository
import com.example.flowka.repositories.material.RemoteMaterialRepository
import com.example.flowka.repositories.remote.FlowkaApi
import com.example.flowka.repositories.service.RemoteServiceRepository
import com.example.flowka.repositories.service.ServiceRepository
import com.example.flowka.repositories.tool.RemoteToolRepository
import com.example.flowka.repositories.tool.ToolRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ClientRepository> { RemoteClientRepository(get<FlowkaApi>()) }
    single<ServiceRepository> { RemoteServiceRepository(get<FlowkaApi>()) }
    single<MaterialRepository> { RemoteMaterialRepository(get<FlowkaApi>()) }
    single<ToolRepository> { RemoteToolRepository(get<FlowkaApi>()) }
}