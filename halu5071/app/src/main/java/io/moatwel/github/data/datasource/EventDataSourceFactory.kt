package io.moatwel.github.data.datasource

import android.arch.paging.DataSource
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.entity.event.Event
import javax.inject.Inject

class EventDataSourceFactory @Inject constructor(
  private val api: EventApi
) : DataSource.Factory<Int, Event> {

  override fun create(): DataSource<Int, Event> {
    return CloudEventDataSource(api)
  }
}