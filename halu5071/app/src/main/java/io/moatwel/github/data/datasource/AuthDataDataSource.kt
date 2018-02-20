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
import androidx.content.edit
import com.squareup.moshi.Moshi
import io.moatwel.github.BuildConfig
import io.moatwel.github.R
import io.moatwel.github.data.network.retrofit.AccessTokenApi
import io.moatwel.github.domain.entity.AuthData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AuthDataDataSource (
  private val context: Context,
  private val moshi: Moshi
) {

  fun saveToSharedPreference(authData: AuthData) {
    val sharedPreferences = context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val adapter = moshi.adapter(AuthData::class.java)
    val jsonResource = adapter.toJson(authData)

    sharedPreferences.edit {
      putString(ARG_AUTH_DATA, jsonResource)
    }
  }

  fun readFromSharedPreference(): AuthData? {
    val sharedPreferences = context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val jsonResource = sharedPreferences.getString(ARG_AUTH_DATA, "")

    return if (jsonResource.isBlank()) {
      null
    } else {
      moshi.adapter(AuthData::class.java).fromJson(jsonResource)
    }
  }

  fun removeFromSharedPreference() {
    val sharedPreferences = context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.remove(ARG_AUTH_DATA)

    editor.apply()
  }

  fun fetchFromApi(code: String, clientId: String, clientSecret: String): Observable<String> {
    // TODO: replace this implementation to Retrofit after creating StringConverterFactory
    val retrofit = Retrofit.Builder()
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .baseUrl(context.getString(R.string.str_access_token_url))
      .build()

    val api = retrofit.create(AccessTokenApi::class.java)
    return api.fetchAccessToken(code, clientId, clientSecret)
  }

  companion object {
    const val ARG_PREFERENCE_NAME = "GITHUB_CLIENT_PREFERENCE"

    const val ARG_AUTH_DATA = "GITHUB_AUTH_DATA"
  }
}