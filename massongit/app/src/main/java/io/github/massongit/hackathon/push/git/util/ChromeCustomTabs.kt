package io.github.massongit.hackathon.push.git.util

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import io.github.massongit.hackathon.push.git.R
import org.chromium.customtabsclient.shared.CustomTabsHelper

/**
 * Chrome Custom Tabs
 * @param context Context
 */
class ChromeCustomTabs(private val context: Context) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = ChromeCustomTabs::class.simpleName
    }

    /**
     * Client
     */
    private var customTabsClient: CustomTabsClient? = null

    /**
     * Connection
     */
    private var customTabsServiceConnection: CustomTabsServiceConnection? = null

    /**
     * Chrome Custom Tabsをバインドする
     */
    fun bind() {
        Log.v(ChromeCustomTabs.TAG, "bind called")
        this.customTabsServiceConnection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
                customTabsClient = client
                customTabsClient?.warmup(0)
            }

            override fun onServiceDisconnected(name: ComponentName) {}
        }
        CustomTabsClient.bindCustomTabsService(this.context, CustomTabsHelper.getPackageNameToUse(this.context), this.customTabsServiceConnection)
    }

    /**
     * Chrome Custom Tabsのバインドを解除する
     */
    fun unbind() {
        Log.v(ChromeCustomTabs.TAG, "unbind called")
        this.context.unbindService(this.customTabsServiceConnection)
    }

    /**
     * Chrome Custom Tabsでサイトを開く
     * @param uri URL
     */
    fun launch(uri: Uri) {
        Log.v(ChromeCustomTabs.TAG, "launch called")
        val session = this.customTabsClient?.newSession(null)
        if (session != null && session.mayLaunchUrl(uri, null, null)) {
            CustomTabsIntent.Builder(session).apply {
                setShowTitle(true)
                setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }.build().launchUrl(this.context, uri)
        } else {
            Toast.makeText(this.context, this.context.getString(R.string.not_found), Toast.LENGTH_SHORT).show()
        }
    }
}
