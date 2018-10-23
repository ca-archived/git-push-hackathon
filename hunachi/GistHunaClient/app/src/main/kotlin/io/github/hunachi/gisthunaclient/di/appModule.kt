package io.github.hunachi.gisthunaclient.di

import io.github.hunachi.gisthunaclient.flux.actionCreator.CreateGistActionCreator
import io.github.hunachi.gisthunaclient.flux.actionCreator.GistListActionCreator
import io.github.hunachi.gisthunaclient.flux.store.GistListStore
import io.github.hunachi.gisthunaclient.flux.actionCreator.MainActionCreator
import io.github.hunachi.gisthunaclient.flux.store.CreateGistStore
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    viewModel { GistListStore(get()) }
    factory { GistListActionCreator(get(), get()) }

    factory { MainActionCreator(get(), get()) }

    viewModel { CreateGistStore(get()) }
    factory { CreateGistActionCreator(get(), get()) }
}