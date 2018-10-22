package io.github.hunachi.oauth

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.github.hunachi.oauth.databinding.ActivityOauthBinding
import io.github.hunachi.oauth.di.oauthModule
import io.github.hunachi.shared.*
import io.github.hunachi.user.di.userModule

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.standalone.StandAloneContext.loadKoinModules

class OAuthActivity : AppCompatActivity() {

    private val oauthActionCreator: OAuthActionCreator by inject()
    private val oauthStore: OAuthStore by viewModel()
    private val preference: SharedPreferences by inject()

    val binding by lazyFast {
        DataBindingUtil.setContentView<ActivityOauthBinding>(this, R.layout.activity_oauth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(listOf(oauthModule, userModule))

        binding.button.setOnClickListener {
            if (netWorkCheck()) oauthActionCreator.igniteOauth()
            else toast("ネット環境を確認してにゃ！")
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
                preference.token()?.let { oauthActionCreator.loadUser(it) }
            }

            userState.nonNullObserve(this@OAuthActivity) {
                preference.ownerName(it.login)
                Toast.makeText(this@OAuthActivity, "こんにちは！${it.login}さん．", Toast.LENGTH_SHORT).show()
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
