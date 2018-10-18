package io.github.hunachi.gisthunaclient.di

import io.github.hunachi.gisthunaclient.flux.GistListActionCreator
import io.github.hunachi.gisthunaclient.flux.GistListStore
import io.github.hunachi.shared.setupSharedPreference
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    factory { setupSharedPreference(get()) }

    viewModel { GistListStore(get()) }

    factory { GistListActionCreator(get(), get()) }
}