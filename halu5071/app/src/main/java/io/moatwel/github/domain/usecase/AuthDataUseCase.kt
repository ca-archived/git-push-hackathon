/*
 *  GitHub-Client
 *
 *  AuthDataUseCase.kt
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

package io.moatwel.github.domain.usecase

import com.squareup.moshi.Moshi
import io.moatwel.github.domain.entity.AuthData
import io.moatwel.github.domain.repository.AuthDataRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 *  This class is Singleton, and manage AuthData
 */
class AuthDataUseCase @Inject constructor(
  private val authDataRepository: AuthDataRepository,
  private val moshi: Moshi
) {

  // User AuthData
  private var authData: AuthData? = null

  fun save(authData: AuthData) {
    authDataRepository.save(authData)
    this.authData = authData
  }

  fun get(): AuthData? {
    return authData
  }

  /**
   *  load auth data from somewhere.
   *  if it is valid, return true and keep it.
   *
   *  @return true if load successfully
   */
  fun load(): Boolean {
    val jsonResource = authDataRepository.get()
    if (jsonResource == "") return false

    val data = moshi.adapter(AuthData::class.java).fromJson(jsonResource)
    data?.let {
      this.authData = it
    } ?: return false

    return true
  }

  fun fetch(code: String): Observable<AuthData> {
    return authDataRepository.fetch(code)
  }
}