package masegi.sho.sharehub.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import androidx.net.toUri
import masegi.sho.sharehub.R
import masegi.sho.sharehub.presentation.event.MainActivity
import masegi.sho.sharehub.presentation.event.MainFragment
import masegi.sho.sharehub.presentation.login.SplashScreenFragment
import masegi.sho.sharehub.util.CustomTabsHelper
import javax.inject.Inject

/**
 * Created by masegi on 2018/02/10.
 */

class NavigationController @Inject constructor(private val activity: AppCompatActivity) {


    // MARK: - Property

    private val containerId: Int = R.id.content
    private val fragmentManager: FragmentManager = activity.supportFragmentManager


    // MARK: - Internal

    fun navigateToSplash() {

        replaceFragment(SplashScreenFragment.newInstance())
    }

    fun navigateToMain() {

        replaceFragment(MainFragment.newInstance())
    }

    fun navigateToMainActivity() {

        MainActivity.start(activity)
    }

    fun replaceFragment(fragment: Fragment) {

        val transaction = fragmentManager
                .beginTransaction()
                .replace(containerId, fragment, null)
        if (fragmentManager.isStateSaved) {

            transaction.commitAllowingStateLoss()
        }
        else {

            transaction.commit()
        }
    }

    fun navigationToExternalBrowser(url: String) {

        val customTabsPackageName = CustomTabsHelper.getPackageNameToUse(activity)
        val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(activity, R.color.color_primary))
                .build()
                .apply {

                    val referrer = "android-app://${activity.packageName}".toUri()
                    intent.putExtra(Intent.EXTRA_REFERRER, referrer)
                }
        val webUri = url.toUri()
        if (tryUsingCustomTabs(customTabsPackageName, customTabsIntent, webUri)) {

            return
        }

        activity.startActivity(customTabsIntent.intent.setData(webUri))
    }


    // MARK: - Private

    private fun tryUsingCustomTabs(customTabsPackageName: String?,
                                   customTabsIntent: CustomTabsIntent,
                                   webUri: Uri?): Boolean {

        customTabsPackageName?.let {

            customTabsIntent.intent.`package` = customTabsPackageName
            customTabsIntent.launchUrl(activity, webUri)
            return true
        }
        return false
    }


    // MARK: - Interface

    interface FragmentReplaceable {

        fun replaceFragment(fragment: Fragment)
    }
}
