package io.moatwel.github.data.datasource

import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.entity.event.Event
import io.reactivex.Observable
import javax.inject.Inject

class CloudEventDataSource @Inject constructor(
  private val api: EventApi
) {

  fun getList(name: String, page: Int): Observable<List<Event>> {
    return api.getList(name, page)
  }
}