package io.moatwel.github.data.repository

import io.moatwel.github.data.datasource.CloudEventDataSource
import javax.inject.Inject

class EventDataRepository @Inject constructor(
  private val cloudEventDataSource: CloudEventDataSource
){

}