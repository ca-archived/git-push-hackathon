package io.github.hunachi.oauth

import io.github.hunachi.user.UserResult

internal sealed class OAuthAction {

    class IgniteOauth(val url: String): OAuthAction()

    class SaveToken(val token: String): OAuthAction()

    class IsLoading(val isLoading: Boolean): OAuthAction()

    class UpdateUser(val userResult: UserResult): OAuthAction()

    object IsError: OAuthAction()
}