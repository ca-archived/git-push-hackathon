package io.github.hunachi.oauthnetwork

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface OauthClient {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("access_token")
    fun accessToken(
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("code")code: String
    ): Deferred<Token>
}

