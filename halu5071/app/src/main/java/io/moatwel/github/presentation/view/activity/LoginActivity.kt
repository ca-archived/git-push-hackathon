/*
 *  GitHub-Client
 *
 *  LoginActivity.kt
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

package io.moatwel.github.presentation.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import dagger.android.support.DaggerAppCompatActivity
import io.moatwel.github.BuildConfig
import io.moatwel.github.R
import io.moatwel.github.data.repository.UserDataRepository
import io.moatwel.github.domain.entity.AuthData
import io.moatwel.github.domain.repository.AuthDataRepository
import io.moatwel.github.domain.repository.UserRepository
import io.moatwel.github.presentation.util.observeOnMainThread
import io.moatwel.github.presentation.util.subscribeOnIoThread
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var authDataRepository: AuthDataRepository

  @Inject
  lateinit var userRepository: UserRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    loginButton.setOnClickListener {
      openCustomTabs()
    }
  }

  private fun openCustomTabs() {
    val url = getString(R.string.str_oath_url, BuildConfig.CLIENT_ID, SCOPE)
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
  }

  override fun onNewIntent(intent: Intent) {
    val action = intent.action
    if (action != Intent.ACTION_VIEW) {
      return
    }
    val uri = intent.data
    uri?.let {
      val code = it.getQueryParameter("code")
      getAccessToken(code)
    } ?: throw RuntimeException("code must not be null")
  }

  private fun getAccessToken(code: String) {
    authDataRepository.fetch(code)
      .subscribeOnIoThread()
      .observeOnMainThread()
      .subscribe({
        saveAuthData(it)
      }, {
        Timber.e(it)
      })
  }

  private fun saveAuthData(authData: AuthData) {
    authDataRepository.save(authData)
    userRepository.loadUser()
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
  }

  companion object {
    const val SCOPE = "user%20repo"
  }
}