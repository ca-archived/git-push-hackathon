package io.moatwel.github

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import io.moatwel.github.di.AppInjector

class Application : Application() {

  override fun onCreate() {
    super.onCreate()

    // Leak Canary
    LeakCanary.install(this)

    // Dagger
    setupDagger()
  }

  private fun setupDagger() {
    AppInjector.init(this)
  }
}