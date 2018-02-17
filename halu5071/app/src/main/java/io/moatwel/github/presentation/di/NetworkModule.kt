/*
 *
 *  GitHub-Client
 *
 *  NetworkModule.kt
 *
 *  Copyright 2018 moatwel.io
 *  author : halu5071 (Yasunori Horii)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.moatwel.github.presentation.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import io.moatwel.github.data.network.AppJsonAdapterFactory
import io.moatwel.github.data.network.HeaderInterceptor
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.data.network.retrofit.UserApi
import io.moatwel.github.domain.usecase.AuthDataUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class NetworkModule {

  /**
   *  provide Retrofit instance.
   *  This Retrofit instance will be singleton.
   */
  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://api.github.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
  }

  /**
   *  provide OkHttp instance.
   *  This OkHttp instance will not be singleton. Because authorization header should be
   *  injected when we me access token from GitHub.
   *
   *  @param authDataUseCase AuthDataUseCase
   */
  @Provides
  fun provideOkHttp(authDataUseCase: AuthDataUseCase): OkHttpClient {
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    return OkHttpClient.Builder()
      .addNetworkInterceptor(StethoInterceptor())
      .addInterceptor(HeaderInterceptor(authDataUseCase))
      .addInterceptor(logger)
      .build()
  }

  /**
   *  provide Moshi instance.
   */
  @Provides
  @Singleton
  fun provideMoshi(): Moshi {
    return Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .add(AppJsonAdapterFactory.INSTANCE)
      .add(Date::class.java, Rfc3339DateJsonAdapter())
      .build()
  }

  @Provides
  @Singleton
  fun provideUserApi(retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
  }

  @Provides
  @Singleton
  fun provideEventApi(retrofit: Retrofit): EventApi {
    return retrofit.create(EventApi::class.java)
  }
}