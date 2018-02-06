package io.moatwel.github.data.repository

import io.moatwel.github.data.datasource.CloudEventDataSource
import io.moatwel.github.domain.entity.event.Event
import io.moatwel.github.domain.repository.EventRepository
import io.reactivex.Observable
import javax.inject.Inject

class EventDataRepository @Inject constructor(
  private val cloudEventDataSource: CloudEventDataSource
): EventRepository{

  override fun eventList(name: String, page: Int): Observable<List<Event>> {
    return cloudEventDataSource.getList(name, page)
  }
}