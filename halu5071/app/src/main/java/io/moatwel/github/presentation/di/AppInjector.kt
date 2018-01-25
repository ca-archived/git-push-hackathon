package io.moatwel.github.presentation.di

import io.moatwel.github.Application
import io.moatwel.github.data.di.DaggerNetworkComponent
import io.moatwel.github.data.di.NetworkModule

class AppInjector {

  companion object {
    fun init(app: Application) {
      // Setting for presentation layer
      DaggerAppComponent.builder()
        .addNetworkModule(NetworkModule.instance)
        .application(app)
        .build()
        .inject(app)

//      // Setting for data layer
//      DaggerNetworkComponent.builder()
//        .addNetworkModule(NetworkModule.instance)
//        .build()
//        .inject(app)
    }
  }
}