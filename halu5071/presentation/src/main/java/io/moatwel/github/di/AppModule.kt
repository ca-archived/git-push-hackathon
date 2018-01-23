package io.moatwel.github.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
  @Provides @Singleton
  fun provideContext(application: Application): Context = application
}