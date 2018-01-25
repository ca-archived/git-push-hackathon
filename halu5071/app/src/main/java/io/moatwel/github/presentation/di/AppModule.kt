package io.moatwel.github.presentation.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.moatwel.github.presentation.di.activity.MainActivityComponent
import javax.inject.Singleton

@Module(subcomponents = [
  MainActivityComponent::class
])
class AppModule {
  @Provides
  @Singleton
  fun provideContext(application: Application): Context = application
}