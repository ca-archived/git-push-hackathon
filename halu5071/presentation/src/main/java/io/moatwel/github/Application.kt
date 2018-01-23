package io.moatwel.github

import android.app.Application
import io.moatwel.github.di.AppInjector

class Application : Application() {

  override fun onCreate() {
    super.onCreate()
    setupDagger()
  }

  private fun setupDagger() {
    AppInjector.init(this)
  }
}