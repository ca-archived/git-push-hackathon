package io.moatwel.github.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.moatwel.github.Application
import io.moatwel.github.data.di.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AndroidInjectionModule::class,
  AppModule::class,
  NetworkModule::class
])
interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance fun application(app: Application): Builder
    fun addNetworkModule(networkModule: NetworkModule): Builder
    fun build(): AppComponent
  }

  fun inject(app: Application)
}