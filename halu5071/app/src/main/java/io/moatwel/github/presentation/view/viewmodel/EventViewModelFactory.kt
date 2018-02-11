package io.moatwel.github.presentation.view.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.usecase.UserUseCase

class EventViewModelFactory(
  private val api: EventApi,
  private val userUseCase: UserUseCase
) : ViewModelProvider.Factory {

  @SuppressWarnings("unchecked")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
      return EventViewModel(api, userUseCase) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }

}