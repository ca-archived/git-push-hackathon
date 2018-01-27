/*
 *  GitHub-Client
 *
 *  AuthDataRepository.kt
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

package io.moatwel.github.domain.repository

import io.moatwel.github.domain.entity.AuthData
import io.reactivex.Observable

interface AuthDataRepository {

  /**
   *  save user access token.
   *
   *  @param authData : auth data contains user access token
   */
  fun save(authData: AuthData)

  /**
   *  read auth data from some where
   *
   *  @return String which present json resouce of auth data or empty
   */
  fun get(): String

  /**
   *  remove auth data
   */
  fun delete()

  /**
   *  fetch auth data from somewhere
   */
  fun fetch(code: String): Observable<AuthData>
}