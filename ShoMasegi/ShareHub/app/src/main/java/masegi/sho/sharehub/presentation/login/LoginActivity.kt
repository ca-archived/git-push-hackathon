package masegi.sho.sharehub.presentation.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

import masegi.sho.sharehub.R
import masegi.sho.sharehub.databinding.ActivityLoginBinding
import masegi.sho.sharehub.presentation.NavigationController
import masegi.sho.sharehub.presentation.common.BaseActivity
import masegi.sho.sharehub.util.GithubLoginUtils
import javax.inject.Inject


class LoginActivity : BaseActivity(), NavigationController.FragmentReplaceable {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val loginViewModel: LoginViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityLoginBinding>(
                this,
                R.layout.activity_login
        )
    }


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_primary)
        if (savedInstanceState == null && binding != null) {

            navigationController.navigateToSplash()
        }
    }

    override fun onNewIntent(intent: Intent?) {

        super.onNewIntent(intent)
        onHandleAuthIntent(intent)
        setIntent(null)
    }


    // MARK: - Private

    private fun onHandleAuthIntent(intent: Intent?) {

        if (intent != null && intent.data != null) {

            val uri = intent.data
            if (uri.toString().startsWith(GithubLoginUtils.redirectUrl)) {

                val tokenCode = uri.getQueryParameter("code")
                if(!tokenCode.isNullOrEmpty()) {

                    loginViewModel.handleAuth(tokenCode)
                }
            }
        }
    }


    // MARK: - FragmentReplaceable

    override fun replaceFragment(fragment: Fragment) {

        navigationController.replaceFragment(fragment)
    }
}
