package io.github.hunachi.oauth

import android.net.Uri
import io.github.hunachi.oauth_usecase.OauthUseCase
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.user_usecase.UserUseCase

internal class OauthActionCreator(
        private val dispatcher: Dispatcher,
        private val oauthUseCase: OauthUseCase,
        private val userUseCase: UserUseCase
) {

    private val PARAM_CODE = "code"
    private val PARAM_STATE = "state"

    fun igniteOauth() {
        dispatcher.send(OauthAction.IgniteOauth(oauthUseCase.getOauthUrl()))
    }

    fun sendCode(uri: Uri) {
        val state = uri.getQueryParameter(PARAM_STATE)
        val code = uri.getQueryParameter(PARAM_CODE)

        if (state == OauthUseCase.STATE_CODE && code != null) {
            dispatcher.send(OauthAction.ReceiveOauthResult(oauthUseCase.register(code)))
        }
    }

    fun loadUser(token: String) {
        dispatcher.send(OauthAction.UpdateUser(userUseCase.setUp(null, token)))
    }
}
