package net.matsudamper.git_push_hackathon

import android.support.v4.app.FragmentTransaction
import net.matsudamper.git_push_hackathon.ui.events.EventsFragment
import net.matsudamper.git_push_hackathon.ui.license.LicenseFragment
import net.matsudamper.git_push_hackathon.ui.login.LoginFragment

class NavigationController(mainActivity: MainActivity) {

    private val containerId = R.id.container
    private val fragmentManager = mainActivity.supportFragmentManager

    fun navigateToLogin() {
        val fragment = LoginFragment()
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(containerId, fragment)
                .commitAllowingStateLoss()
    }

    fun navigationToEvents() {
        val fragment = EventsFragment()
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(containerId, fragment)
                .commitAllowingStateLoss()
    }

    fun navigationToLicense() {
        val fragment = LicenseFragment()
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }
}