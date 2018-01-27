package io.moatwel.github.presentation.di.activity

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import io.moatwel.github.presentation.view.activity.LoginActivity

@Module
interface LoginActivityModule {
  @Binds
  fun provideLoginActivity(loginActivity: LoginActivity): AppCompatActivity
}