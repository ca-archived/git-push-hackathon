package io.github.massongit.hackathon.push.git.login.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.login.helper.LoginHelper

/**
 * ログイン画面のActivity
 */
class LoginActivity : AppCompatActivity() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = LoginActivity::class.simpleName
    }

    /**
     * Helper
     */
    private lateinit var loginHelper: LoginHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(LoginActivity.TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_login)
        this.loginHelper = LoginHelper(this, this.findViewById(R.id.login_button))
    }

    override fun onNewIntent(intent: Intent) {
        Log.v(LoginActivity.TAG, "onNewIntent called")
        super.onNewIntent(intent)
        if (intent.data != null) {
            this.loginHelper.startLoginActivity(intent)
        }
    }

    override fun onDestroy() {
        Log.v(LoginActivity.TAG, "onDestroy called")
        this.loginHelper.unbindChromeCustomTabs()
        super.onDestroy()
    }

    /**
     * ログインボタン押下時のイベント
     * @param v View
     */
    fun onLoginButtonClick(v: View) {
        Log.v(LoginActivity.TAG, "onLoginButtonClick called")
        this.loginHelper.authorize()
    }
}
