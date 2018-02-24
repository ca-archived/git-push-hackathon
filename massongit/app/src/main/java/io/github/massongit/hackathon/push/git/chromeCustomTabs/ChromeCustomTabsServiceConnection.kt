package io.github.massongit.hackathon.push.git.chromeCustomTabs

import android.content.ComponentName
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsServiceConnection
import android.util.Log

/**
 *　Chrome Custom Tabs Service Connection
 * @param helper Helper
 * @param afterWarmUpEvent Warm Up後に実行するイベント
 */
class ChromeCustomTabsServiceConnection(private val helper: ChromeCustomTabsHelper, private val afterWarmUpEvent: (() -> Unit)?) : CustomTabsServiceConnection() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = ChromeCustomTabsServiceConnection::class.simpleName
    }

    override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
        Log.v(ChromeCustomTabsServiceConnection.TAG, "onCustomTabsServiceConnected called")
        this.helper.customTabsClient = client.apply {
            warmup(0)
        }
        if (this.afterWarmUpEvent != null) {
            this.afterWarmUpEvent.invoke()
        }
    }

    override fun onServiceDisconnected(name: ComponentName) {

    }
}
