package io.github.massongit.hackathon.push.git.helper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
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
 * Chrome Custom Tabs Helper
 * @param context Context
 */
class ChromeCustomTabsHelper(private val context: Context) {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = ChromeCustomTabsHelper::class.simpleName
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
     * @param afterWarmUpEvent Warm Up後に実行するイベント
     */
    fun bind(afterWarmUpEvent: (() -> Unit)? = null) {
        Log.v(ChromeCustomTabsHelper.TAG, "bind called")
        this.customTabsServiceConnection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
                customTabsClient = client
                customTabsClient?.warmup(0)
                if (afterWarmUpEvent != null) {
                    afterWarmUpEvent()
                }
            }

            override fun onServiceDisconnected(name: ComponentName) {}
        }
        CustomTabsClient.bindCustomTabsService(this.context, CustomTabsHelper.getPackageNameToUse(this.context), this.customTabsServiceConnection)
    }

    /**
     * Chrome Custom Tabsのバインドを解除する
     * @param afterUnbindEvent バインドを解除後に実行するイベント
     */
    fun unbind(afterUnbindEvent: (() -> Unit)? = null) {
        Log.v(ChromeCustomTabsHelper.TAG, "unbind called")
        this.context.unbindService(this.customTabsServiceConnection)
        if(afterUnbindEvent!=null){
            afterUnbindEvent()
        }
    }

    /**
     * Chrome Custom Tabsでサイトを開く
     * @param uri URL
     */
    fun launch(uri: Uri) {
        Log.v(ChromeCustomTabsHelper.TAG, "launch called")
        val session = this.customTabsClient?.newSession(null)
        if (session != null && session.mayLaunchUrl(uri, null, null)) {
            CustomTabsIntent.Builder(session).apply {
                setShowTitle(true)
                setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }.build().apply {
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }.launchUrl(this.context, uri)
        } else {
            Toast.makeText(this.context, this.context.getString(R.string.not_found), Toast.LENGTH_SHORT).show()
        }
    }
}
