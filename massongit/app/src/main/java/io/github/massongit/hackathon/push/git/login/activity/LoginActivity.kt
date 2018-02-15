package io.github.massongit.hackathon.push.git.login.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.helper.ChromeCustomTabsHelper
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

    /**
     * Chrome Custom Tabs Helper
     */
    private lateinit var chromeCustomTabsHelper: ChromeCustomTabsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(LoginActivity.TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_login)
        this.loginHelper = LoginHelper()
        this.chromeCustomTabsHelper = ChromeCustomTabsHelper(this)
    }

    override fun onStart() {
        Log.v(LoginActivity.TAG, "onStart called")
        super.onStart()
        this.chromeCustomTabsHelper.bind()
    }

    override fun onStop() {
        Log.v(LoginActivity.TAG, "onStop called")
        this.chromeCustomTabsHelper.unbind()
        super.onStop()
        this.finish()
    }

    /**
     * ログインボタン押下時のイベント
     * @param v View
     */
    fun onLoginButtonClick(v: View) {
        Log.v(LoginActivity.TAG, "onLoginButtonClick called")
        this.loginHelper.authorize(this, this.chromeCustomTabsHelper)
    }
}
