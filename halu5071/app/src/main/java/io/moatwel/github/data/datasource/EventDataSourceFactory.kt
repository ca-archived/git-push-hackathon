package io.moatwel.github.data.datasource

import android.arch.paging.DataSource
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.entity.event.Event
import io.moatwel.github.domain.usecase.UserUseCase

class EventDataSourceFactory (
  private val api: EventApi,
  private val userUseCase: UserUseCase
) : DataSource.Factory<Int, Event> {

  override fun create(): DataSource<Int, Event> {
    return CloudEventDataSource(api, userUseCase)
  }
}