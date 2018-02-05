package io.github.massongit.hackathon.push.git.util

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import io.github.massongit.hackathon.push.git.R
import org.chromium.customtabsclient.shared.CustomTabsHelper

/**
 * CustomTabを起動する
 * @param context Activity
 * @param uri URL
 */
fun launchCustomTab(context: Context, uri: Uri) {
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
