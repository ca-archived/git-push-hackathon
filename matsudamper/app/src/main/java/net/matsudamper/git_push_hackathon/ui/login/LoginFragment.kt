package net.matsudamper.git_push_hackathon.ui.login

import android.animation.Animator
import android.animation.ObjectAnimator
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.net.toUri
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import net.matsudamper.git_push_hackathon.MainActivity
import net.matsudamper.git_push_hackathon.R
import net.matsudamper.git_push_hackathon.appdata.AppData
import net.matsudamper.git_push_hackathon.databinding.LoginFragmentBinding
import net.matsudamper.git_push_hackathon.github.GitHubClient


class LoginFragment : Fragment() {

    private val client by lazy {
        GitHubClient(resources.getString(R.string.client_id), resources.getString(R.string.client_secret))
    }

    private val navigationController by lazy {
        (activity as MainActivity).navigationController
    }

    private lateinit var binding: LoginFragmentBinding
    private lateinit var appData: AppData

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<LoginFragmentBinding>(inflater, R.layout.login_fragment, container, false).apply {
            vm = ViewModelProviders.of(this@LoginFragment).get<LoginViewModel>(LoginViewModel::class.java).apply {
                clickListener = View.OnClickListener {
                    val uri = client.getAuthenticationUrl().toUri()

                    Intent(Intent.ACTION_VIEW, uri).let {
                        startActivity(it)
                    }
                }
            }
        }

        activity?.let {
            appData = AppData(it)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {

            val dataString = activity?.intent?.dataString
            activity?.intent?.data = null
            if (dataString != null) {
                launch(UI) {
                    binding.vm?.state = LoginViewModel.State.NOW

                    val token = client.getAccessToken(dataString).await()
                    if (token != null) {

                        val user = client.setToken(token).getMyProfile().await()
                        if (user != null) {
                            binding.vm?.state = LoginViewModel.State.COMPLETE

                            appData.token = token
                            appData.id = user.id
                            appData.name = user.login

                            navigationController.navigationToEvents()
                        } else {
                            binding.vm?.state = LoginViewModel.State.FAILED
                        }
                    } else {
                        binding.vm?.state = LoginViewModel.State.FAILED
                    }
                }
            }
        }
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        when (transit) {
            FragmentTransaction.TRANSIT_FRAGMENT_OPEN -> {
                if (enter.not()) {
                    return ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
                }
            }
        }

        return super.onCreateAnimator(transit, enter, nextAnim)
    }
}