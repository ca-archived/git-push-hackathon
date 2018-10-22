package io.github.hunachi.gist.di

import io.github.hunachi.gist.GistLocalRepository
import io.github.hunachi.gist.GistRepository
import io.github.hunachi.gistnetwork.GistClientFactory
import org.koin.dsl.module.module

val gistModule = module {
    val GIST_CLIENT = "gistClient"

    single { GistRepository(get(GIST_CLIENT), get()) }

    factory { GistLocalRepository(get()) }

    single(GIST_CLIENT) { GistClientFactory.gistClientInstance() }
}