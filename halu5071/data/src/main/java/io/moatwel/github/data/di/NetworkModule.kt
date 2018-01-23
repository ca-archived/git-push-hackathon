package io.moatwel.github.data.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

  companion object {
    val instance = NetworkModule()
  }

  @Provides @Singleton
  fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://api.github.com")
      .build()
  }
}