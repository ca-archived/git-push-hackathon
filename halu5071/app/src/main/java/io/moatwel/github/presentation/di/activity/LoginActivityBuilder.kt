package io.moatwel.github.presentation.di.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.moatwel.github.presentation.view.activity.LoginActivity

@Module
interface LoginActivityBuilder {
  @ContributesAndroidInjector(
    modules = [
      LoginActivityModule::class
    ]
  )
  fun contributeLoginActivity(): LoginActivity
}