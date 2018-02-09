package io.github.massongit.hackathon.push.git.util

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.util.Log
import io.github.massongit.hackathon.push.git.R
import org.chromium.customtabsclient.shared.CustomTabsHelper

class Util {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = Util::class.simpleName

        /**
         * CustomTabを起動する
         * @param context Activity
         * @param uri URL
         */
        fun launchCustomTab(context: Context, uri: Uri) {
            Log.v(Util.TAG, "launchCustomTab called")
            CustomTabsIntent.Builder().apply {
                setShowTitle(true)
                setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }.build().apply {
                val packageName = CustomTabsHelper.getPackageNameToUse(context)
                if (packageName != null) {
                    intent.`package` = packageName
                }
            }.launchUrl(context, uri)
        }
    }
}