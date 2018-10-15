package io.github.hunachi.shared

import org.koin.dsl.module.module

val dispatcherModule = module {
    single { Dispatcher() }
}