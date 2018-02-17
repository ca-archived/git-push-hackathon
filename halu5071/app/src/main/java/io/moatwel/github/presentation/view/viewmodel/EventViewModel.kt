/*
 *  GitHub-Client
 *
 *  EventViewModel.kt
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

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.moatwel.github.data.datasource.EventDataSourceFactory
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.entity.event.Event
import io.moatwel.github.domain.usecase.UserUseCase

class EventViewModel(
  api: EventApi,
  userUseCase: UserUseCase
) : ViewModel() {

  private val factory: EventDataSourceFactory = EventDataSourceFactory(api, userUseCase)

  val events: LiveData<PagedList<Event>>

  init {
    val config = PagedList.Config.Builder()
      .setInitialLoadSizeHint(2)
      .setPrefetchDistance(PAGE_SIZE)
      .setPageSize(PAGE_SIZE)
      .build()

    events = LivePagedListBuilder(factory, config).build()
  }

  companion object {
    private const val PAGE_SIZE = 15
  }
}