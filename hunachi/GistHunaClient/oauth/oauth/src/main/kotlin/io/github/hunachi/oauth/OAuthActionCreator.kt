package io.github.hunachi.oauth

import android.net.Uri
import android.util.Log
import io.github.hunachi.oauthnetwork.OauthRepository
import io.github.hunachi.oauthnetwork.Token
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.shared.network.Result
import io.github.hunachi.user.UserRepository
import kotlinx.coroutines.experimental.*
import java.lang.Exception

internal class OAuthActionCreator(
        private val dispatcher: Dispatcher,
        private val oauthRepository: OauthRepository,
        private val userRepository: UserRepository
) {

    private var job: Job? = null
    private val PARAM_CODE = "code"
    private val PARAM_STATE = "state"

    fun igniteOauth() {
        dispatcher.send(OAuthAction.IgniteOauth(oauthRepository.getOauthUrl()))
    }

    fun sendCode(uri: Uri) {
        val state = uri.getQueryParameter(PARAM_STATE)
        val code = uri.getQueryParameter(PARAM_CODE)

        if (state == OauthRepository.STATE_CODE && code != null) {
            job = CoroutineScope(Dispatchers.IO).launch {
                dispatcher.send(OAuthAction.IsLoading(true))
                val token: Result<Token, Exception> = async { oauthRepository.register(code) }.await()

                when (token) {
                    is Result.Success -> {
                        dispatcher.send(OAuthAction.SaveToken(token.data.token))
                    }
                    is Result.Error -> {
                        Log.d(token.e.message, "eror")
                        dispatcher.send(OAuthAction.IsError)
                    }
                }
                dispatcher.send(OAuthAction.IsLoading(false))
            }

            //repeat() を使ってもいいかも．
        }
    }

    fun loadUser(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dispatcher.send(OAuthAction.UpdateUser(userRepository.setUp(null, token)))
        }
    }

    fun stopLoading() {
        job?.cancel()
    }
}
