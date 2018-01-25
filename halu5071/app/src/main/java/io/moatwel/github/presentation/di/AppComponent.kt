package io.moatwel.github.presentation.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.moatwel.github.Application
import io.moatwel.github.presentation.di.activity.MainActivityBuilder
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AndroidInjectionModule::class,
  AppModule::class,
  MainActivityBuilder::class,
  NetworkModule::class,
  RepositoryModule::class,
  DatasourceModule::class,
  UsecaseModule::class
])
interface AppComponent{

  @Component.Builder
  interface Builder {
    @BindsInstance fun application(app: Application): Builder
    fun addNetworkModule(networkModule: NetworkModule): Builder
    fun addUsecaseModule(usecaseModule: UsecaseModule): Builder
    fun addRepositoryModule(repositoryModule: RepositoryModule): Builder
    fun addDatasourceModule(datasourceModule: DatasourceModule): Builder
    fun build(): AppComponent
  }

  fun inject(app: Application)
}