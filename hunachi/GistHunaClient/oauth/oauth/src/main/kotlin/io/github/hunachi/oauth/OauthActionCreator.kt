package io.github.hunachi.oauth

import android.net.Uri
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.user.UserRepository
import kotlinx.coroutines.experimental.*

internal class OauthActionCreator(
        private val dispatcher: Dispatcher,
        private val oauthRepository: OauthRepository,
        private val userRepository: UserRepository
) {

    private var job: Job? = null
    private val PARAM_CODE = "code"
    private val PARAM_STATE = "state"

    fun igniteOauth() {
        dispatcher.send(OauthAction.IgniteOauth(oauthRepository.getOauthUrl()))
    }

    fun sendCode(uri: Uri) {
        val state = uri.getQueryParameter(PARAM_STATE)
        val code = uri.getQueryParameter(PARAM_CODE)

        if (state == OauthRepository.STATE_CODE && code != null) {
            CoroutineScope(Dispatchers.IO).launch{
                dispatcher.send(OauthAction.ReceiveOauthResult(oauthRepository.register(code)))
            }
        }
    }

    fun loadUser(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dispatcher.send(OauthAction.UpdateUser(userRepository.setUp(null, token)))
        }
    }

    fun stopLoading() {
        job?.cancel()
    }
}
