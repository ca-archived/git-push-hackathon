package io.github.hunachi.oauth_infra

import kotlinx.coroutines.experimental.runBlocking

class OauthRepository internal constructor(private val oauthClient: OauthClient) {

    suspend fun accessToken(code: String) = runBlocking {
        oauthClient.accessToken(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                code
        ).await()
    }
}