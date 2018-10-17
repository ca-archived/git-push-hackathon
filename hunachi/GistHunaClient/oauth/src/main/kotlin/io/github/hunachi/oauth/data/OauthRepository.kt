package io.github.hunachi.oauth.data

import io.github.hunachi.oauth.BuildConfig
import io.github.hunachi.shared.Result
import kotlinx.coroutines.experimental.runBlocking

class OauthRepository(private val oauthService: OauthService, private val url: String) {

    companion object {
        const val STATE_CODE = "gist-hunachi"
    }

    suspend fun register(code: String) = runBlocking {
        try {
            val token = oauthService.accessToken(
                    BuildConfig.CLIENT_ID,
                    BuildConfig.CLIENT_SECRET,
                    code
            ).await()
            Result.Success<Token, Exception>(token)
        } catch (e: Exception) {
            Result.Error<Token, Exception>(IllegalStateException())
        }
    }

    fun getOauthUrl() = url + "&state=" + STATE_CODE
}