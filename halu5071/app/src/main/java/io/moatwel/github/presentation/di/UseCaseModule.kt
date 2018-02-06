/*
 *
 *  GitHub-Client
 *
 *  UsecaseModule.kt
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

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.moatwel.github.domain.repository.AuthDataRepository
import io.moatwel.github.domain.repository.EventRepository
import io.moatwel.github.domain.repository.UserRepository
import io.moatwel.github.domain.usecase.AuthDataUseCase
import io.moatwel.github.domain.usecase.EventUseCase
import io.moatwel.github.domain.usecase.UserUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {

  @Provides
  fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
    return UserUseCase(userRepository)
  }

  @Provides
  fun provideEventUseCase(eventRepository: EventRepository): EventUseCase {
    return EventUseCase(eventRepository)
  }

  /**
   *  This AuthDataUseCase is provided as Singleton.
   *  Because this usecase manage user auth data whole application lifecycle.
   */
  @Provides
  @Singleton
  fun provideAuthDataUseCase(authDataRepository: AuthDataRepository, moshi: Moshi): AuthDataUseCase {
    return AuthDataUseCase(authDataRepository, moshi)
  }

  companion object {
    val INSTANCE = UseCaseModule()
  }
}