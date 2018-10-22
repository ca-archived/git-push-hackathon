package io.github.hunachi.usernetwork

import io.github.hunachi.model.User
import io.github.hunachi.usernetwork.model.UserJson
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface UserClient {

    @GET("user")
    fun owner(
            @Query("access_token") token: String
    ): Deferred<User>
}