package io.github.hunachi.oauth_usecase

interface OauthUseCase {

    companion object {
        const val STATE_CODE = "gist-hunachi"
    }

    fun register(code: String): OauthResult

    fun getOauthUrl(): String
}