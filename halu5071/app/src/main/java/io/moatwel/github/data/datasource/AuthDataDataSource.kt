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
import com.squareup.moshi.Moshi
import io.moatwel.github.domain.entity.AuthData
import javax.inject.Inject

class AuthDataDataSource @Inject constructor(
  private val context: Context
) {

  @Inject lateinit var moshi: Moshi

  fun saveToSharedPreference(authData: AuthData) {
    val sharedPreferences = context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val adapter = moshi.adapter(AuthData::class.java)
    val jsonResource = adapter.toJson(authData)

    editor.putString(ARG_AUTH_DATA, jsonResource)

    editor.apply()
  }

  fun readFromSharedPreference(): AuthData {
    val sharedPreferences = context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val adapter = moshi.adapter(AuthData::class.java)

    val jsonResource = sharedPreferences.getString(ARG_AUTH_DATA, "")
    if (jsonResource == "") {
      throw RuntimeException("Failed to load auth data")
    }
    val authData = adapter.fromJson(jsonResource)

    editor.apply()
    authData?.let {
      return it
    }?: throw RuntimeException("AuthData is null")
  }

  fun removeFromSharedPreference() {
    val sharedPreferences = context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.remove(ARG_AUTH_DATA)

    editor.apply()
  }

  companion object {
    const val ARG_PREFERENCE_NAME = "GITHUB_CLIENT_PREFERENCE"

    const val ARG_AUTH_DATA = "GITHUB_AUTH_DATA"
  }
}