package io.moatwel.github.data.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AccessTokenApi {
  @GET("/login/oauth/access_token")
  fun fetchAccessToken(
    @Query("code") code: String,
    @Query("client_id") clientId: String,
    @Query("client_secret") clientSecret: String
  ): Observable<String>
}