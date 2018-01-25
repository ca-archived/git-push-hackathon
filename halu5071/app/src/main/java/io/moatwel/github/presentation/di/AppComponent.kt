package io.moatwel.github.presentation.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.moatwel.github.Application
import io.moatwel.github.presentation.di.activity.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AndroidInjectionModule::class,
  AppModule::class,
  MainActivityModule::class
])
interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance fun application(app: Application): Builder
//    fun addNetworkModule(networkModule: NetworkModule): Builder
    fun build(): AppComponent
  }

  fun inject(app: Application)
}