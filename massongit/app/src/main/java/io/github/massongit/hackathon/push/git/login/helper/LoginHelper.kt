package io.github.massongit.hackathon.push.git.login.helper

import android.app.Activity
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.util.Log
import com.github.scribejava.apis.GitHubApi
import com.github.scribejava.core.builder.ServiceBuilder
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.application.MainApplication
import org.chromium.customtabsclient.shared.CustomTabsHelper

/**
 * ログイン画面のHelper
 */
class LoginHelper {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG = LoginHelper::class.simpleName
    }

    /**
     * GitHub APIの認証を行う
     * @param activity Activity
     */
    fun authorize(activity: Activity) {
        Log.v(LoginHelper.TAG, "authorize called")

        val application = activity.application as MainApplication

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

        Log.d(LoginHelper.TAG, "authUrl=" + authUrl)

        // GitHub APIの連携アプリ認証画面を表示
        CustomTabsIntent.Builder().apply {
            setToolbarColor(R.style.AppTheme)
            setShowTitle(true)
        }.build().apply {
            val packageName = CustomTabsHelper.getPackageNameToUse(activity)
            if (packageName != null) {
                intent.`package` = packageName
            }
        }.launchUrl(activity, Uri.parse(authUrl))
    }
}