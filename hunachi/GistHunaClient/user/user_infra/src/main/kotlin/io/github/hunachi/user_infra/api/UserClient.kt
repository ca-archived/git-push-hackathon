package io.github.hunachi.user_infra.api

import io.github.hunachi.model.User
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

internal interface UserClient {

    @GET("user")
    fun owner(
            @Query("access_token") token: String
    ): Deferred<User>
}