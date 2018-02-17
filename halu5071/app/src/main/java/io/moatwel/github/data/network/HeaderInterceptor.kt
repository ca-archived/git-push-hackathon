/*
 *  GitHub-Client
 *
 *  HeaderInterceptor.kt
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

package io.moatwel.github.data.network

import io.moatwel.github.domain.usecase.AuthDataUseCase
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor (
  private val authDataUseCase: AuthDataUseCase
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    val token = authDataUseCase.get()?.token ?: ""

    val newRequest = request.newBuilder()
      .addHeader("Authorization", "token $token")
      .build()

    return chain.proceed(newRequest)
  }
}