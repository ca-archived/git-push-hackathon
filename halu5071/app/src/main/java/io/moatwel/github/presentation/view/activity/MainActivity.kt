package io.moatwel.github.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.moatwel.github.R
import io.moatwel.github.domain.usecase.UserUseCase
import io.moatwel.github.presentation.util.observeOnMainThread
import io.moatwel.github.presentation.util.subscribeOnIoThread
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var userUsecase: UserUseCase

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    userUsecase.get()
      .subscribeOnIoThread()
      .observeOnMainThread()
      .subscribe({
        Timber.d("User: ${it.name}")
      }, {
        Timber.e(it)
      })
  }
}
