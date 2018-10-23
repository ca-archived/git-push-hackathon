package io.github.hunachi.oauth

import io.github.hunachi.user.model.UserResult

internal sealed class OauthAction {

    class IgniteOauth(val url: String): OauthAction()

    class ReceiveOauthResult(val oauthResult: OauthResult): OauthAction()

    class UpdateUser(val userResult: UserResult): OauthAction()
}