package io.moatwel.github.domain.repository

import io.moatwel.github.domain.entity.event.Event
import io.reactivex.Observable
import retrofit2.Response

interface EventRepository {

  fun eventList(name: String, page: Int = 1): Observable<Response<List<Event>>>
}