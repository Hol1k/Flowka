package com.example.flowka.di

import com.example.flowka.repositories.client.ClientRepository
import com.example.flowka.viewmodels.clients.ClientViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ClientViewModel(get<ClientRepository>()) }
}