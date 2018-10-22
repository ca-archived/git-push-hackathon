package io.github.hunachi.gisthunaclient.di

import io.github.hunachi.database.MyDatabase
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.shared.setupSharedPreference
import org.koin.dsl.module.module

val coreModule = module {

    single { Dispatcher() }

    single { MyDatabase.getInstance(get()) }

    factory { setupSharedPreference(get()) }
}