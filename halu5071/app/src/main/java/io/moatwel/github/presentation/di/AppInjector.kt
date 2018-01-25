package io.moatwel.github.presentation.di

import io.moatwel.github.Application

class AppInjector {

  companion object {
    fun init(app: Application) {
      DaggerAppComponent.builder()
        .addNetworkModule(NetworkModule.INSTANCE)
        .addRepositoryModule(RepositoryModule.INSTANCE)
        .addDatasourceModule(DataSourceModule.INSTANCE)
        .addUsecaseModule(UseCaseModule.INSTANCE)
        .application(app)
        .build()
        .inject(app)
    }
  }
}