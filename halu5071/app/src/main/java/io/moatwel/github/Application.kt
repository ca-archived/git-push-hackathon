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