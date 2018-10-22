package io.github.hunachi.gistnetwork

import io.github.hunachi.gistnetwork.model.GistJson
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GistClient {

    @GET("users/{username}/gists")
    fun gists(
            @Path("username") userName: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
            @Query("access_token") token: String
    ): Deferred<List<GistJson>>

    @GET("gists")
    fun publicGists(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int
    ): Deferred<List<GistJson>>

    @GET("gists/{gistId}")
    fun gist(
            @Path("gist_id") gistId: String,
            @Query("access_token") token: String
    ): Deferred<GistJson>
}