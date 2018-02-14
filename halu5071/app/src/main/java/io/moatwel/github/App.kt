/*
 *  GitHub-Client
 *
 *  Application.kt
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

package io.moatwel.github

import android.support.text.emoji.EmojiCompat
import android.support.text.emoji.bundled.BundledEmojiCompatConfig
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import io.moatwel.github.domain.usecase.AuthDataUseCase
import io.moatwel.github.presentation.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication(), HasActivityInjector {

  @Inject
  lateinit var authDataUseCase: AuthDataUseCase

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
    DaggerAppComponent.builder()
      .application(this)
      .build()


  override fun onCreate() {
    super.onCreate()
    setupEmoji()
    setupLeakCanary()
    setupStetho()
    setupTimber()
    loadAuthData()
  }

  private fun loadAuthData() {
    authDataUseCase.load()
  }

  private fun setupTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  private fun setupStetho() {
    Stetho.initializeWithDefaults(this)
  }

  private fun setupLeakCanary() {
    LeakCanary.install(this)
  }

  private fun setupEmoji() {
    val config = BundledEmojiCompatConfig(this)
    EmojiCompat.init(config)
  }
}