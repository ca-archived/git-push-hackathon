package io.moatwel.github.data.network

import io.moatwel.github.domain.entity.event.Event
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {

  @GET("/users/{username}/received_events")
  fun getList(@Path("username") name: String,
              @Query("page") page: Int): Observable<Response<List<Event>>>
}