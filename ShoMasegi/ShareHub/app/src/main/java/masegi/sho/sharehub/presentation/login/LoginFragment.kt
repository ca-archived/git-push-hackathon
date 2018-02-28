package masegi.sho.sharehub.presentation.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.florent37.viewanimator.ViewAnimator
import dagger.android.support.DaggerFragment

import masegi.sho.sharehub.R
import masegi.sho.sharehub.databinding.FragmentLoginBinding
import masegi.sho.sharehub.presentation.NavigationController
import masegi.sho.sharehub.util.GithubLoginUtils
import masegi.sho.sharehub.util.ext.observeNonNull
import masegi.sho.sharehub.util.ext.setUsable
import masegi.sho.sharehub.util.ext.setVisible
import javax.inject.Inject

class LoginFragment : DaggerFragment() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentLoginBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.loginBrowserButton.setOnClickListener {

            navigationController.navigationToExternalBrowser(GithubLoginUtils.authorizationUrl.toString())
        }
        setupLoginManage()
        binding.loginButton.setOnClickListener {

            val username = binding.userName.text.toString()
            val password = binding.password.text.toString()
            val twoFactor = binding.twoFactor.text.toString()
            loginViewModel.login(username, password, twoFactor)
        }
        startAnimation()
    }


    // MARK: - Private

    private fun setupLoginManage() {

        loginViewModel.isLoginSuccess.observeNonNull(this, {

            when(it) {
                true -> {

                    navigationController.navigateToMainActivity()
                    activity!!.finish()
                }
                false -> {

                    Toast.makeText(context, R.string.login_fail_cant_get_token_data, Toast.LENGTH_LONG)
                            .show()
                }
            }
        })
        loginViewModel.isLoading.observeNonNull(this, {

            binding.loginButton.setUsable(!it)
            binding.loginBrowserButton.setUsable(!it)
            binding.loginProgress.setVisible(it)
        })
        loginViewModel.isTwoFactor.observeNonNull(this, {

            if (it) {

                binding.twoFactorForm.setUsable(it)
                Toast.makeText(context, R.string.login_require_two_factor, Toast.LENGTH_LONG)
                        .show()
            }
        })
    }

    private fun startAnimation() {

        ViewAnimator
                .animate(binding.loginTopImage)
                    .dp().translationY(144F, 0F)
                    .decelerate()
                    .duration(1000)
                .start()

        ViewAnimator
                .animate(binding.formParentLayout)
                    .dp().translationY(144F, 0F)
                    .duration(1000)
                .andAnimate(binding.formParentLayout)
                    .alpha(0F, 0F, 0F, 1F)
                    .decelerate()
                    .duration(1000)
                .start()
    }


    companion object {

        fun newInstance(): LoginFragment = LoginFragment()
    }
}
