package io.github.hunachi.gisthunaclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.github.hunachi.gisthunaclient.databinding.ActivityMainBinding
import io.github.hunachi.oauth.OauthActivity

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            button.setOnClickListener {
                startActivity(Intent(this@MainActivity, OauthActivity::class.java))
            }
        }
    }
}
