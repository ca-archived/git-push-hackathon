package io.github.hunachi.oauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.github.hunachi.oauth.databinding.ActivityOauthBinding

class OauthActivity : AppCompatActivity() {

    val binding by lazy {
        DataBindingUtil.setContentView<ActivityOauthBinding>(this, R.layout.activity_oauth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {  }
    }

    companion object {
        fun newInstance() = OauthActivity()
    }
}
