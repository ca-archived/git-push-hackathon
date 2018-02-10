package io.github.massongit.hackathon.push.git.login.helper

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.github.scribejava.apis.GitHubApi
import com.github.scribejava.core.builder.ServiceBuilder
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.application.MainApplication
import io.github.massongit.hackathon.push.git.util.ChromeCustomTabs

/**
 * ログイン画面のHelper
 * @param activity Activity
 */
class LoginHelper(private val activity: Activity) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = LoginHelper::class.simpleName
    }

    /**
     * Chrome Custom Tabs
     */
    private val chromeCustomTabs: ChromeCustomTabs = ChromeCustomTabs(this.activity)

    /**
     * Chrome Custom Tabsをバインドする
     */
    fun bindChromeCustomTabs() {
        Log.v(LoginHelper.TAG, "bindChromeCustomTabs called")
        this.chromeCustomTabs.bind()
    }

    /**
     * Chrome Custom Tabsのバインドを解除する
     */
    fun unbindChromeCustomTabs() {
        Log.v(LoginHelper.TAG, "unbindChromeCustomTabs called")
        this.chromeCustomTabs.unbind()
    }

    /**
     * GitHub APIの認証を行う
     */
    fun authorize() {
        Log.v(LoginHelper.TAG, "authorize called")
        val application = this.activity.application
        if (application is MainApplication) {
            if (application.service == null) {
                application.service = ServiceBuilder(this.activity.getString(R.string.client_id)).apply {
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
            this.chromeCustomTabs.launch(Uri.parse(authUrl))
        } else {
            Log.v(LoginHelper.TAG, "Authorize Error!")
            Toast.makeText(this.activity, this.activity.getString(R.string.error_happen), Toast.LENGTH_LONG).show()
        }
    }
}