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

import android.app.Activity
import android.app.Application
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.moatwel.github.presentation.di.AppInjector
import timber.log.Timber
import javax.inject.Inject

class Application : Application(), HasActivityInjector {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

  override fun onCreate() {
    super.onCreate()

    // Leak Canary
    LeakCanary.install(this)

    // Timber
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    // Dagger
    setupDagger()
  }

  private fun setupDagger() {
    AppInjector.init(this)
  }
}