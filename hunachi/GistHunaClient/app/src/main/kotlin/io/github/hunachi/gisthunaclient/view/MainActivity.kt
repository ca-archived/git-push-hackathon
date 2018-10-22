package io.github.hunachi.gisthunaclient.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import io.github.hunachi.gisthunaclient.R
import io.github.hunachi.gisthunaclient.databinding.ActivityMainBinding
import io.github.hunachi.oauth.OAuthActivity
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
            startActivity(OAuthActivity.newInstance())
            preference.firstCheckIn()
        } else if (preference.token() == null) {
            preference.token() ?: tokenIsDuplicatedOrFailed()
        } else {
            setupView()
        }
    }

    private fun setupView() {
        binding.apply {
            text.text = "認証に成功しています！"
            button.isVisible = false
        }
        supportFragmentManager.inTransaction {
            replace(R.id.list_container, GistListFragment.newInstance())
        }
    }

    fun tokenIsDuplicatedOrFailed() {
        Toast.makeText(this, getString(R.string.sorry_aouth_toast_text), Toast.LENGTH_SHORT).show()
        startActivity(OAuthActivity.newInstance())
    }
}
