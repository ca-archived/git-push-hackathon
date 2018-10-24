package io.github.hunachi.oauth

import io.github.hunachi.oauth_usecase.OauthResult
import io.github.hunachi.user_usecase.model.UserResult

internal sealed class OauthAction {

    class IgniteOauth(val url: String): OauthAction()

    class ReceiveOauthResult(val oauthResult: OauthResult): OauthAction()

    class UpdateUser(val userResult: UserResult): OauthAction()
}