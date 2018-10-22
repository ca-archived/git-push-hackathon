package io.github.hunachi.gisthunaclient.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.github.hunachi.gisthunaclient.R
import io.github.hunachi.gisthunaclient.databinding.ActivityMainBinding
import io.github.hunachi.oauth.OauthActivity
import io.github.hunachi.shared.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val preference: SharedPreferences by inject()

    val binding by lazyFast {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preference.isFirstUser()) {
            startActivity(OauthActivity.newInstance())
            preference.firstCheckIn()
        } else if (preference.token() == null) {
            preference.token() ?: tokenIsDuplicatedOrFailed()
        } else {
            supportFragmentManager.inTransaction {
                replace(binding.listContainer.id, GistListFragment.newInstance())
            }
        }
    }

    fun tokenIsDuplicatedOrFailed() {
        Toast.makeText(this, getString(R.string.sorry_aouth_toast_text), Toast.LENGTH_SHORT).show()
        startActivity(OauthActivity.newInstance())
    }
}
