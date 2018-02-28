package masegi.sho.sharehub.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import android.util.Log
import androidx.net.toUri

/**
 * Created by masegi on 2018/02/10.
 */

object CustomTabsHelper {


    // MARK: - Property

    private const val STABLE_PACKAGE = "com.android.chrome"
    private const val BETA_PACKAGE = "com.chrome.beta"
    private const val DEV_PACKAGE = "com.chrome.dev"
    private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"
    private const val ACTION_CUSTOM_TABS_CONNECTION =
            "android.support.customtabs.action.CustomTabsService"

    private var packageNameToUse: String? = null


    // MARK: - Internal

    fun getPackageNameToUse(context: Context): String? {

        packageNameToUse?.let { return it }

        val packageManager = context.packageManager
        val activityIntent = Intent(Intent.ACTION_VIEW, "http://www.example.com".toUri())
        val defaultViewHandlerInfo = packageManager.resolveActivity(activityIntent, 0)
        var defaultViewHandlerPackageName: String? = null
        if (defaultViewHandlerInfo != null) {

            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName
        }

        val resolvedActivityList = packageManager.queryIntentActivities(activityIntent, 0)
        val packagesSupportingCustomTabs = mutableListOf<String>()
        for (info in resolvedActivityList) {

            val serviceIntent = Intent()
            serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
            serviceIntent.`package` = info.activityInfo.packageName
            if (packageManager.resolveService(serviceIntent, 0) != null) {

                packagesSupportingCustomTabs.add(info.activityInfo.packageName)
            }
        }

        if (packagesSupportingCustomTabs.isEmpty()) {

            packageNameToUse = null
        }
        else if (packagesSupportingCustomTabs.size == 1) {

            packageNameToUse = packagesSupportingCustomTabs[0]
        }
        else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
            && !hasSpecializedHandlerIntents(context, activityIntent)
            && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {

            packageNameToUse = defaultViewHandlerPackageName
        }
        else {

            packageNameToUse = arrayOf(STABLE_PACKAGE, BETA_PACKAGE, DEV_PACKAGE, LOCAL_PACKAGE)
                    .firstOrNull { packagesSupportingCustomTabs.contains(it) }
        }
        return packageNameToUse
    }


    // MARK: - Private

    private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {

        try {

            val packageManager = context.packageManager
            val handlers = packageManager.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER)
            if (handlers == null || handlers.size == 0) {

                return false
            }
            for (resolveInfo in handlers) {

                val filter = resolveInfo.filter ?: continue
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
                if (resolveInfo.activityInfo == null) continue
                return true
            }
        }
        catch (e: RuntimeException) {

            Log.e("CustomTabsHelper", "Runtime exception while getting specialized handlers")
        }
        return false
    }
}