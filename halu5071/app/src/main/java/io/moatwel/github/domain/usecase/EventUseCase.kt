package io.moatwel.github.domain.usecase

import io.moatwel.github.domain.repository.EventRepository
import javax.inject.Inject

class EventUseCase @Inject constructor(
  private val eventRepository: EventRepository
){

}