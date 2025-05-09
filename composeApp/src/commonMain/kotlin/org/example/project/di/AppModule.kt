package org.example.project.di


import org.example.project.data.repo.BlogRepository
import org.example.project.domain.BlogApi
import org.example.project.presentation.viewModel.BlogViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // API
    single { BlogApi.create() }

    // Repository
    singleOf(::BlogRepository)

    // ViewModel
    factory { BlogViewModel(get()) }
}
