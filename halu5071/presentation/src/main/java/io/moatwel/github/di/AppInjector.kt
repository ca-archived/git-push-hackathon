package io.moatwel.github.di

import io.moatwel.github.Application
import io.moatwel.github.data.di.NetworkModule

class AppInjector {

  companion object {
    fun init(app: Application) {
      DaggerAppComponent.builder()
        .application(app)
        .addNetworkModule(NetworkModule.instance)
        .build()
        .inject(app)
    }
  }
}