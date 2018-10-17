package io.github.hunachi.oauth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.github.hunachi.oauth.databinding.ActivityOauthBinding
import io.github.hunachi.shared.lazyFast
import io.github.hunachi.shared.nonNullObserve
import io.github.hunachi.shared.observe

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OAuthActivity : AppCompatActivity() {

    private val oauthActionCreator: OAuthActionCreator by inject()
    private val oauthStore: OAuthStore by viewModel()

    val binding by lazyFast {
        DataBindingUtil.setContentView<ActivityOauthBinding>(this, R.layout.activity_oauth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.button.setOnClickListener {
            oauthActionCreator.igniteOauth()
        }

        oauthStore.apply {
            isLoadingState.nonNullObserve(this@OAuthActivity) {
                binding.button.isClickable = !it
            }

            isErrorState.observe(this@OAuthActivity) {
                Log.d("tag", "isErrorState is working.")
            }

            oauthUrlState.nonNullObserve(this@OAuthActivity) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            }

            isSuccessState.observe(this@OAuthActivity) {
                Toast.makeText(this@OAuthActivity, "uriが届いた", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.run {
            onCreate()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let { uri ->
            oauthActionCreator.sendCode(uri)
        }
    }

    override fun onDestroy() {
        oauthActionCreator.stopLoading()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = OAuthActivity()
    }
}
