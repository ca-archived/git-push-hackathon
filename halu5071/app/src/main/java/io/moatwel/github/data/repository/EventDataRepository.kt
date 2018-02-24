/*
 *  GitHub-Client
 *
 *  EventDataRepository.kt
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

package io.moatwel.github.data.repository

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.moatwel.github.data.datasource.EventDataSourceFactory
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.entity.EventLiveData
import io.moatwel.github.domain.repository.EventRepository
import io.moatwel.github.domain.repository.UserRepository

class EventDataRepository(
  private val api: EventApi,
  private val userRepository: UserRepository
) : EventRepository {

  override fun getEventEntity(): EventLiveData {
    val factory = EventDataSourceFactory(api, userRepository)

    val config = PagedList.Config.Builder()
      .setInitialLoadSizeHint(2)
      .setPrefetchDistance(PAGE_SIZE)
      .setPageSize(PAGE_SIZE)
      .build()

    val events = LivePagedListBuilder(factory, config).build()

    val refreshState = Transformations.switchMap(factory.sourceLiveData) {
      it.networkState
    }

    return EventLiveData(
      pagedList = events,
      refreshState = refreshState,
      refresh = {
        factory.sourceLiveData.value?.invalidate()
      })
  }

  companion object {
    private const val PAGE_SIZE = 15
  }
}