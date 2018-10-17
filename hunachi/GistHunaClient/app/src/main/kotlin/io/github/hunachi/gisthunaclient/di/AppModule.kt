package io.github.hunachi.gisthunaclient.di

import io.github.hunachi.shared.setupSharedPreference
import org.koin.dsl.module.module

val appModule = module {
    factory { setupSharedPreference(get()) }
}