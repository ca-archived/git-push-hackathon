package io.moatwel.github.presentation.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.moatwel.github.BuildConfig
import io.moatwel.github.R
import io.moatwel.github.domain.entity.AuthData
import io.moatwel.github.domain.usecase.AuthDataUseCase
import io.moatwel.github.presentation.util.app
import io.moatwel.github.presentation.util.observeOnMainThread
import io.moatwel.github.presentation.util.subscribeOnIoThread
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

  @Inject
  lateinit var authDataUseCase: AuthDataUseCase

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    loginButton.setOnClickListener {
      openCustomTabs()
    }
  }

  private fun openCustomTabs() {
    val url = getString(R.string.str_oath_url, BuildConfig.CLIENT_ID, SCOPE)
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
  }

  override fun onNewIntent(intent: Intent) {
    val action = intent.action
    if (action != Intent.ACTION_VIEW) {
      return
    }
    val uri = intent.data
    uri?.let {
      val code = it.getQueryParameter("code")
      getAccessToken(code)
    }?: throw RuntimeException("code must not be null")
  }

  private fun getAccessToken(code: String) {
    authDataUseCase.fetch(code)
      .subscribeOnIoThread()
      .observeOnMainThread()
      .subscribe({
        saveAuthData(it)
      }, {
        Timber.e(it)
      })
  }

  private fun saveAuthData(authData: AuthData) {
    authDataUseCase.save(authData)
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
  }

  companion object {
    const val SCOPE = "user%20repo"
  }
}