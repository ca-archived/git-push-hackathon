package io.moatwel.github.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.android.AndroidInjection
import io.moatwel.github.R
import io.moatwel.github.data.datasource.CloudUserDataSource
import io.moatwel.github.data.network.UserApi
import io.moatwel.github.data.repository.UserDataRepository
import io.moatwel.github.domain.usecase.UserUsecase
import io.moatwel.github.presentation.util.observeOnMainThread
import io.moatwel.github.presentation.util.subscribeOnIoThread
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var userUsecase: UserUsecase

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
