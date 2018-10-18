package io.github.hunachi.shared.flux

import org.koin.dsl.module.module

val dispatcherModule = module {
    single { Dispatcher() }
}