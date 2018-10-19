package io.github.hunachi.oauthnetwork

import io.github.hunachi.shared.network.Result
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.coroutineScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

class OauthRepository(private val oauthClient: OauthClient, private val url: String) {

    companion object {
        const val STATE_CODE = "gist-hunachi"
    }

    fun register(code: String): Result<Token, Exception> = runBlocking {
        try {
            val token = oauthClient.accessToken(
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