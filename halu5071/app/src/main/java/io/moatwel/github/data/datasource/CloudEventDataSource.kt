package io.moatwel.github.data.datasource

import io.moatwel.github.data.network.retrofit.EventApi
import javax.inject.Inject

class CloudEventDataSource @Inject constructor(
  private val api: EventApi
) {

}