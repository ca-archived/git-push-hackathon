package io.github.hunachi.gist.di

import io.github.hunachi.gist.GistLocalRepository
import io.github.hunachi.gistlocal.GistDatabase
import io.github.hunachi.gistnetwork.GistClientFactory
import org.koin.dsl.module.module
import java.util.concurrent.Executors

val gistModule = module {

    factory { GistLocalRepository(get(), Executors.newSingleThreadExecutor()) }

    factory { GistClientFactory.gistClientInstance() }

    single { GistDatabase.getInstance(get()) }
}