/*
 *  GitHub-Client
 *
 *  EventViewModelFactory.kt
 *
 *  Copyright 2018 moatwel.io
 *  author : halu5071 (Yasunori Horii)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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