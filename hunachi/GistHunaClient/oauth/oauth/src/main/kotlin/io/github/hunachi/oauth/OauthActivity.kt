package io.github.hunachi.oauth

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import io.github.hunachi.oauth.databinding.ActivityOauthBinding
import io.github.hunachi.oauth.di.oauthModule
import io.github.hunachi.shared.*
import io.github.hunachi.shared.network.NetWorkError
import io.github.hunachi.user.di.userModule

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.standalone.StandAloneContext.loadKoinModules

class OauthActivity : AppCompatActivity() {

    private val oauthActionCreator: OauthActionCreator by inject()
    private val oauthStore: OAuthStore by viewModel()
    private val preference: SharedPreferences by inject()

    val binding by lazyFast {
        DataBindingUtil.setContentView<ActivityOauthBinding>(this, R.layout.activity_oauth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(listOf(oauthModule, userModule))

        binding.authButton.setOnClickListener {
            if (netWorkCheck()) oauthActionCreator.igniteOauth()
            else toastNetworkError(NetWorkError.NORMAL)
        }

        binding.loadingDialog.isVisible = false

        oauthStore.apply {
            isLoadingState.nonNullObserve(this@OauthActivity) {
                binding.authButton.isClickable = !it
                binding.loadingDialog.apply { isVisible = it }.run { if (it) start() else stop() }
            }

            errorState.observe(this@OauthActivity, Observer {
                toastNetworkError(it)
            })

            oauthUrlState.nonNullObserve(this@OauthActivity) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            }

            tokenState.nonNullObserve(this@OauthActivity) {
                preference.token(it.token)
                preference.token()?.let { oauthActionCreator.loadUser(it) }
            }

            userState.nonNullObserve(this@OauthActivity) {
                preference.ownerName(it.login)
                Toast.makeText(this@OauthActivity, "こんにちは！${it.login}さん．", Toast.LENGTH_SHORT).show()
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

    companion object {
        fun newInstance() = OauthActivity()
    }
}
