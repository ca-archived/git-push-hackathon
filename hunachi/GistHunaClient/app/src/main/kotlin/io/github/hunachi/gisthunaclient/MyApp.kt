package io.github.hunachi.gisthunaclient

import android.app.Application
import io.github.hunachi.gisthunaclient.di.appModule
import io.github.hunachi.oauth.di.oauthModule
import io.github.hunachi.shared.dispatcherModule
import org.koin.android.ext.android.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, dispatcherModule, oauthModule))
    }
}