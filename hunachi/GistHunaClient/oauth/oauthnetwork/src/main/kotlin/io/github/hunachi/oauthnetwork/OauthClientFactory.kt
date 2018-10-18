package io.github.hunachi.oauthnetwork

import io.github.hunachi.shared.network.createClient

object OauthClientFactory{

    private const val scopes = "repo,gist"
    private const val baseUrl = "https://github.com/login/oauth/authorize"
    private const val clientId: String = BuildConfig.CLIENT_ID

    const val url = baseUrl + "?" +
            "client_id=" + clientId +
            "&scope=" + scopes

    private val oauthClient: OauthClient = createClient(isLenientMode = true)

    fun oauthClientInstance(): OauthClient = oauthClient
}