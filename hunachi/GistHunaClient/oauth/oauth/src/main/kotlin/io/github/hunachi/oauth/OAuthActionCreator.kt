package io.github.hunachi.oauth

import android.net.Uri
import io.github.hunachi.oauthnetwork.OauthRepository
import io.github.hunachi.oauthnetwork.Token
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.shared.network.Result
import kotlinx.coroutines.experimental.*
import java.lang.Exception

internal class OAuthActionCreator(
        private val dispatcher: Dispatcher,
        private val repository: OauthRepository) {

    private var job: Job? = null
    private val PARAM_CODE = "code"
    private val PARAM_STATE = "state"

    fun igniteOauth() {
        dispatcher.send(OAuthAction.IgniteOauth(repository.getOauthUrl()))
    }

    fun sendCode(uri: Uri) {
        val state = uri.getQueryParameter(PARAM_STATE)
        val code = uri.getQueryParameter(PARAM_CODE)

        if (state == OauthRepository.STATE_CODE && code != null) {
            job = CoroutineScope(Dispatchers.IO).launch {
                dispatcher.send(OAuthAction.IsLoading(true))
                val token: Result<Token, Exception> = repository.register(code)

                when (token) {
                    is Result.Success -> {
                        dispatcher.send(OAuthAction.SaveToken(token.data.token))
                    }
                    is Result.Error -> {
                        dispatcher.send(OAuthAction.IsError)
                    }
                }
                dispatcher.send(OAuthAction.IsLoading(false))
            }

            //repeat() を使ってもいいかも．
        }
    }

    fun stopLoading() {
        job?.cancel()
    }
}
