package io.github.massongit.hackathon.push.git.login.helper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.github.scribejava.apis.GitHubApi
import com.github.scribejava.core.builder.ServiceBuilder
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.application.MainApplication
import io.github.massongit.hackathon.push.git.helper.ChromeCustomTabsHelper
import io.github.massongit.hackathon.push.git.main.activity.MainActivity

/**
 * ログイン画面のHelper
 * @param activity Activity
 * @param loginButton ログインボタン
 */
class LoginHelper(private val activity: Activity, private val loginButton: TextView) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = LoginHelper::class.simpleName
    }

    /**
     * Chrome Custom Tabs Helper
     */
    private val chromeCustomTabsHelper: ChromeCustomTabsHelper = ChromeCustomTabsHelper(this.activity)

    init {
        this.chromeCustomTabsHelper.bind({
            this.loginButton.isEnabled = true
        })
    }

    /**
     * Chrome Custom Tabsをのバインドを解除する
     */
    fun unbindChromeCustomTabs() {
        Log.v(LoginHelper.TAG, "unbindChromeCustomTabs called")
        this.chromeCustomTabsHelper.unbind({
            this.loginButton.isEnabled = false
        })
    }

    /**
     * GitHub APIの認証を行う
     */
    fun authorize() {
        Log.v(LoginHelper.TAG, "authorize called")
        val application = this.activity.application
        if (application is MainApplication) {
            if (application.service == null) {
                application.service = ServiceBuilder(activity.getString(R.string.client_id)).apply {
                    apiSecret(activity.getString(R.string.client_secret))
                    callback(Uri.Builder().apply {
                        scheme(activity.getString(R.string.callback_url_scheme))
                        authority(activity.getString(R.string.callback_url_host))
                        path(activity.getString(R.string.callback_url_path))
                    }.build().toString())
                }.build(GitHubApi.instance())
            }

            // GitHub APIの認証ページURL
            val authUrl = application.service?.authorizationUrl

            Log.d(LoginHelper.TAG, "authUrl: " + authUrl)

            // GitHub APIの連携アプリ認証画面を表示
            this.chromeCustomTabsHelper.launch(Uri.parse(authUrl))
        } else {
            Log.v(LoginHelper.TAG, "Authorize Error!")
            Toast.makeText(this.activity, activity.getString(R.string.error_happen), Toast.LENGTH_LONG).show()
        }
    }

    /**
     * LoginActivityをスタートする
     * @param intent Chrome Custom TabsからのIntent
     */
    fun startLoginActivity(intent: Intent) {
        Log.v(LoginHelper.TAG, "startLoginActivity called")
        this.activity.startActivity(Intent(this.activity, MainActivity::class.java).apply {
            data = intent.data
        })
        this.activity.finish()
    }
}