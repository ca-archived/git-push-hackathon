package io.moatwel.github.presentation.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.android.AndroidInjection
import io.moatwel.github.R
import io.moatwel.github.domain.usecase.UserUsecase
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  lateinit var userUsecase: UserUsecase

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    userUsecase.get().subscribe({
      Timber.d(TAG, "User: $it")
    }, {

    })
  }

  companion object {
    val TAG = MainActivity::class.java.simpleName
  }
}
