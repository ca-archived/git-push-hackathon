package io.moatwel.github.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.moatwel.github.Application
import javax.inject.Singleton

@Singleton
@Component(modules = [
  AndroidInjectionModule::class,
  AppModule::class
])
interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance fun application(app: Application): Builder
    fun build(): AppComponent
  }

  fun inject(app: Application)
}