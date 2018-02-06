/*
 *  GitHub-Client
 *
 *  MainActivity.kt
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
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.moatwel.github.R
import io.moatwel.github.domain.usecase.AuthDataUseCase
import io.moatwel.github.domain.usecase.EventUseCase
import io.moatwel.github.domain.usecase.UserUseCase
import io.moatwel.github.presentation.util.observeOnMainThread
import io.moatwel.github.presentation.util.subscribeOnIoThread
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var userUseCase: UserUseCase

  @Inject
  lateinit var authDataUseCase: AuthDataUseCase

  @Inject
  lateinit var eventUseCase: EventUseCase

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (authDataUseCase.get() == null) {
      val intent = Intent(this, LoginActivity::class.java)
      startActivity(intent)
    }

    userUseCase.get()
      .subscribeOnIoThread()
      .observeOnMainThread()
      .subscribe({
        Timber.d("User Login: ${it.login}")
      }, {
        Timber.e(it)
      })

    eventUseCase.getEventList("halu5071", 1)
      .subscribeOnIoThread()
      .observeOnMainThread()
      .subscribe({
        it.forEach {
          Timber.d("Type: ${it.type}")
        }
      }, {
          Timber.e(it)
      })
  }
}
