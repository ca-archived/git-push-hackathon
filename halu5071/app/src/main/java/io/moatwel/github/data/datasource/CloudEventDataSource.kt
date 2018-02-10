package io.moatwel.github.data.datasource

import android.arch.paging.PageKeyedDataSource
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.entity.event.Event
import io.moatwel.github.domain.usecase.UserUseCase
import javax.inject.Inject

class CloudEventDataSource @Inject constructor(
  private val api: EventApi,
  private val userUseCase: UserUseCase
) : PageKeyedDataSource<Int, Event>(){

  private fun getList(name: String,
                      page: Int,
                      callback: (events: List<Event>, next: Int?) -> Unit) {
    api.getList(name, page).
      subscribe({

      }, {

      })
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Event>) {
    getList("halu5071", 1) { events, next ->
      callback.onResult(events, null, next)
    }
  }
}