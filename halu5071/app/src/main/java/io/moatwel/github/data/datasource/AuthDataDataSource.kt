/*
 *  GitHub-Client
 *
 *  AuthDataDataSource.kt
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

package io.moatwel.github.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.content.edit
import com.squareup.moshi.Moshi
import io.moatwel.github.BuildConfig
import io.moatwel.github.R
import io.moatwel.github.domain.entity.AuthData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request

class AuthDataDataSource (
  private val context: Context,
  private val moshi: Moshi
) {

  private val sharedPreferences: SharedPreferences

  init {
    sharedPreferences = context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE)
  }

  fun saveToSharedPreference(authData: AuthData) {
    val adapter = moshi.adapter(AuthData::class.java)
    val jsonResource = adapter.toJson(authData)

    sharedPreferences.edit {
      putString(ARG_AUTH_DATA, jsonResource)
    }
  }

  fun readFromSharedPreference(): AuthData? {
    val jsonResource = sharedPreferences.getString(ARG_AUTH_DATA, "")

    return moshi.adapter(AuthData::class.java).fromJson(jsonResource)
  }

  fun removeFromSharedPreference() {
    val editor = sharedPreferences.edit()

    editor.remove(ARG_AUTH_DATA)

    editor.apply()
  }

  fun fetchFromApi(code: String): Observable<String> = Observable.create {
    // TODO: replace this implementation to Retrofit after creating StringConverterFactory
    val request = Request.Builder()
      .url(context.getString(R.string.str_access_token_url,
        code, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET))
      .build()

    val okHttpClient = OkHttpClient.Builder()
      .build()

    val response = okHttpClient.newCall(request).execute()
    when (response.code()) {
      in 300..399 -> it.onError(RuntimeException("Redirecting"))
      in 400..499 -> it.onError(RuntimeException("Network Exception"))
      in 500..599 -> it.onError(RuntimeException("Server Error"))
      else -> it.onNext(response.body()?.string().toString())
    }
    it.onComplete()
  }

  companion object {
    const val ARG_PREFERENCE_NAME = "GITHUB_CLIENT_PREFERENCE"

    const val ARG_AUTH_DATA = "GITHUB_AUTH_DATA"
  }
}