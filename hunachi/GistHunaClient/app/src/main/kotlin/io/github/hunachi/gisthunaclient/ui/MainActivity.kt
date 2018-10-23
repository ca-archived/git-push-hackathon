package io.github.hunachi.gisthunaclient.ui

import android.content.SharedPreferences
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.github.hunachi.gisthunaclient.R
import io.github.hunachi.gisthunaclient.databinding.ActivityMainBinding
import io.github.hunachi.gisthunaclient.flux.FragmentState
import io.github.hunachi.gisthunaclient.flux.actionCreator.MainActionCreator
import io.github.hunachi.gisthunaclient.ui.gistCreate.CreateGistFragment
import io.github.hunachi.gisthunaclient.ui.gistList.GistListFragment
import io.github.hunachi.oauth.OauthActivity
import io.github.hunachi.shared.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val preference: SharedPreferences by inject()
    private val mainActionCreator: MainActionCreator by inject()

    val binding by lazyFast {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preference.token() != null) preference.login(true)

        binding.fab.setOnClickListener {
            if (!preference.login()) toast(getString(R.string.have_to_login_alart_text))
            else mainActionCreator.clickedFav()
        }
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(FragmentState.GIST_LIST)
    }

    override fun onBackPressed() {
        mainActionCreator.clickedBack()
    }

    fun replaceFragment(fragmentState: FragmentState) {
        supportFragmentManager.inTransaction {
            replace(binding.listContainer.id, when (fragmentState) {
                FragmentState.GIST_LIST -> {
                    binding.fab.setImageIcon(Icon.createWithResource(
                            this@MainActivity, R.drawable.ic_add_white_24dp))
                    GistListFragment.newInstance()
                }
                FragmentState.CREATE_GIST -> {
                    binding.fab.setImageIcon(Icon.createWithResource(
                            this@MainActivity, R.drawable.ic_save_white_24dp))
                    CreateGistFragment.newInstance()
                }
            }.checkAllMatched)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!preference.login()) menuInflater.inflate(R.menu.oauth_setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.invite_oauth -> {
                if (!preference.login()) {
                    preference.login(true)
                    startActivity(OauthActivity.newInstance())
                } else toast(getString(R.string.already_login_text))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun tokenIsDuplicatedOrFailed() {
        Toast.makeText(this, getString(R.string.sorry_oauth_toast_text), Toast.LENGTH_SHORT).show()
        startActivity(OauthActivity.newInstance())
    }
}
