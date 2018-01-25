package io.moatwel.github.presentation.di

import io.moatwel.github.Application

class AppInjector {

  companion object {
    fun init(app: Application) {
      // Setting for presentation layer
      DaggerAppComponent.builder()
        .addNetworkModule(NetworkModule.INSTANCE)
        .addRepositoryModule(RepositoryModule.INSTANCE)
        .addDatasourceModule(DatasourceModule.INSTANCE)
        .addUsecaseModule(UsecaseModule.INSTANCE)
        .application(app)
        .build()
        .inject(app)
    }
  }
}