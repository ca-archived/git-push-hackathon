package masegi.sho.sharehub.presentation.common

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import masegi.sho.sharehub.R
import masegi.sho.sharehub.data.model.NavigationItem
import masegi.sho.sharehub.databinding.NavContentBinding
import masegi.sho.sharehub.presentation.NavigationController
import masegi.sho.sharehub.presentation.common.adapter.NavigationListAdapter
import masegi.sho.sharehub.presentation.common.binding.setImageFromImageurl
import masegi.sho.sharehub.presentation.common.pref.Prefs
import javax.inject.Inject

/**
 * Created by masegi on 2018/02/10.
 */

class DrawerMenu @Inject constructor(
        private val activity: AppCompatActivity,
        private val navigationController: NavigationController
) {


    // MARK: - Property

    private lateinit var drawerLayout: DrawerLayout


    // MARK: - Internal

    fun setup(
            drawerLayout: DrawerLayout,
            contentView: View,
            navigationView: NavigationView,
            navigationContentView: NavContentBinding,
            toolbar: Toolbar? = null,
            actionBarDrawerSync: Boolean = true
    )
    {

        navigationContentView.headerIcon.setImageFromImageurl(Prefs.avatarUrl)
        val items = NavigationItem.values().toList()

        navigationContentView.navMenuList.adapter = NavigationListAdapter(activity, items)
        navigationContentView.navMenuList.divider = null

        this.drawerLayout = drawerLayout
        if (actionBarDrawerSync) {

            object : ActionBarDrawerToggle(
                    activity,
                    drawerLayout,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
            )
            {

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                    contentView.x = navigationView.width * slideOffset
                    toolbar?.let {  it.x = navigationView.width * slideOffset }
                    super.onDrawerSlide(drawerView, 0F)
                }
            }.also {

                drawerLayout.addDrawerListener(it)
            }.apply {

                isDrawerIndicatorEnabled = true
                isDrawerSlideAnimationEnabled = false
                syncState()
            }
        }
        navigationContentView.navMenuList.setOnItemClickListener { _, _, position, _ ->

            items[position].navigate(navigationController)
            drawerLayout.closeDrawers()
        }

    }

    fun closeDrawerIfNeeded(): Boolean {

        return if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawers()
            false
        }
        else {

            true
        }
    }
}

