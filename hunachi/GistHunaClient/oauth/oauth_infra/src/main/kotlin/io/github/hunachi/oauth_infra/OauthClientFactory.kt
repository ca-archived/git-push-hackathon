package io.github.hunachi.oauth_infra

import io.github.hunachi.shared.network.createClient

object OauthClientFactory {

    private const val scopes = "repo,gist"
    private const val baseUrl = "https://github.com/login/oauth/authorize"
    private const val clientId: String = BuildConfig.CLIENT_ID

    const val url = baseUrl + "?" +
            "client_id=" + clientId +
            "&scope=" + scopes

    fun oauthClientInstance(): OauthClient = createClient(
            isLenientMode = true,
            baseUrl = "https://github.com/login/oauth/")
}