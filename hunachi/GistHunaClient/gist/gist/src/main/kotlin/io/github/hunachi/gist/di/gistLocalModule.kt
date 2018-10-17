package io.github.hunachi.gist.data.local.di

import android.app.Application
import io.github.hunachi.gistlocal.GIstLocalAdapter
import org.koin.dsl.module.module

val gistModule = module {

    factory {
        GIstLocalAdapter.gistDatabase(get<Application>()).build()
    }
}