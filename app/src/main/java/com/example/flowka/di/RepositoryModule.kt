package com.example.flowka.di

import com.example.flowka.repositories.client.ClientRepository
import com.example.flowka.repositories.client.RemoteClientRepository
import com.example.flowka.repositories.client.remote.FlowkaApi
import org.koin.dsl.module

val repositoryModule = module {
    single<ClientRepository> { RemoteClientRepository(get<FlowkaApi>()) }
}