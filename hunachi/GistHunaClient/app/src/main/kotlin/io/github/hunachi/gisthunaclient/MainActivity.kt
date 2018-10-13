package io.github.hunachi.gisthunaclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.github.hunachi.gisthunaclient.databinding.ActivityMainBinding
import io.github.hunachi.oauth.OauthActivity
import io.github.hunachi.shared.inTransaction
import io.github.hunachi.shared.startActivity

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    private fun setupView(){
        binding.apply {
            button.setOnClickListener {
                startActivity(OauthActivity.newInstance())
            }
        }
        supportFragmentManager.inTransaction {
            replace(R.id.list_container, GistListFragment.newInstance())
        }
    }
}
