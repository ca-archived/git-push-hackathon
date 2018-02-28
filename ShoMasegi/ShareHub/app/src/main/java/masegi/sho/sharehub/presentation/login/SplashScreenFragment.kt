package masegi.sho.sharehub.presentation.login


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import masegi.sho.sharehub.data.model.login.AccessToken
import masegi.sho.sharehub.databinding.FragmentSplashBinding

import masegi.sho.sharehub.presentation.NavigationController
import masegi.sho.sharehub.presentation.common.pref.Prefs
import masegi.sho.sharehub.util.ext.observeNonNull
import javax.inject.Inject

class SplashScreenFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentSplashBinding
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val loginViewModel: LoginViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSplashBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupLoginManage()
        if (!Prefs.accessToken.isNullOrEmpty()) {

            loginViewModel.getUser(AccessToken(accessToken = Prefs.accessToken))
        }
        else {

            Handler().postDelayed( {

                (activity as NavigationController.FragmentReplaceable).replaceFragment(LoginFragment.newInstance())
            }, 2000)
        }
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

                    (activity as NavigationController.FragmentReplaceable).replaceFragment(LoginFragment.newInstance())
                }
            }
        })
    }

    companion object {

        fun newInstance(): SplashScreenFragment = SplashScreenFragment()
    }

}
