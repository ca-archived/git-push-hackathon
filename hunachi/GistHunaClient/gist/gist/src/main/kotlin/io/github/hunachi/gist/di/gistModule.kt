package io.github.hunachi.gist.di

import io.github.hunachi.gist.local.GistLocalClient
import io.github.hunachi.gist.GistPostRepository
import io.github.hunachi.gist.GistRepository
import io.github.hunachi.gistnetwork.GistClientFactory
import org.koin.dsl.module.module

val gistModule = module {
    val GIST_CLIENT = "gistClient"

    factory { GistRepository(get(GIST_CLIENT), get()) }

    factory { GistPostRepository(get(GIST_CLIENT), get()) }

    single { GistLocalClient(get()) }

    single(GIST_CLIENT) { GistClientFactory.gistClientInstance() }
}