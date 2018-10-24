package io.github.hunachi.oauth_infra.model

import com.squareup.moshi.Json

data class Token(
        @Json(name = "access_token")
        val token: String,
        val scope: String?,
        @Json(name = "token_type")
        val tokenType: String
)