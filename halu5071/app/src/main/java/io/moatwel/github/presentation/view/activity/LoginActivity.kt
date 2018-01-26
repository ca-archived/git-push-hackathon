package io.moatwel.github.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.moatwel.github.R
import kotlinx.android.synthetic.main.activity_login.loginButton

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    loginButton.setOnClickListener {
      openCustomTabs()
    }
  }

  private fun openCustomTabs() {

  }
}