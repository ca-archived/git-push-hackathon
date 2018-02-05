package io.github.massongit.hackathon.push.git.login.activity

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
        private val TAG = LoginActivity::class.simpleName
    }

    /**
     * Helper
     */
    private lateinit var helper: LoginHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(LoginActivity.TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_login)
        this.helper = LoginHelper()
    }

    /**
     * ログインボタン押下時のイベント
     * @param v View
     */
    fun onLoginButtonClick(v: View) {
        Log.v(LoginActivity.TAG, "onLoginButtonClick called")
        this.helper.authorize(this)
    }
}
