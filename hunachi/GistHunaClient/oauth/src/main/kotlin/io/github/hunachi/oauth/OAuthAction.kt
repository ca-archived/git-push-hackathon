package io.github.hunachi.oauth

internal sealed class OAuthAction {

    class IgniteOauth(val url: String): OAuthAction()

    class SaveToken(val token: String): OAuthAction()

    class IsLoading(val isLoading: Boolean): OAuthAction()

    object IsError: OAuthAction()
}