package io.github.massongit.hackathon.push.git.login.helper

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.github.scribejava.apis.GitHubApi
import com.github.scribejava.core.builder.ServiceBuilder
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.application.MainApplication
import io.github.massongit.hackathon.push.git.helper.ChromeCustomTabsHelper

/**
 * ログイン画面のHelper
 */
class LoginHelper {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = LoginHelper::class.simpleName
    }

    /**
     * GitHub APIの認証を行う
     * @param activity Activity
     * @param chromeCustomTabsHelper Chrome Custom Tabs Helper
     */
    fun authorize(activity: Activity, chromeCustomTabsHelper: ChromeCustomTabsHelper) {
        Log.v(LoginHelper.TAG, "authorize called")
        val application = activity.application
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
            chromeCustomTabsHelper.apply {
                launch(Uri.parse(authUrl))
                unbind()
            }
        } else {
            Log.v(LoginHelper.TAG, "Authorize Error!")
            Toast.makeText(activity, activity.getString(R.string.error_happen), Toast.LENGTH_LONG).show()
        }
    }
}