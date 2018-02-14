package io.moatwel.github.data.datasource

import android.arch.paging.PageKeyedDataSource
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.entity.event.Event
import io.moatwel.github.domain.usecase.UserUseCase
import timber.log.Timber
import javax.inject.Inject

class CloudEventDataSource (
  private val api: EventApi,
  private val userUseCase: UserUseCase
) : PageKeyedDataSource<Int, Event>(){

  private fun getList(name: String,
                      page: Int,
                      callback: (events: List<Event>, next: Int?) -> Unit) {
    Timber.d("page: $page")
    api.getList(name, page)
      .subscribe({
        var next: Int? = null
        it.headers().get("Link")?.let {
          val regex = Regex("rel=\"next\"")
          if (regex.containsMatchIn(it)) {
            next = page + 1
            Timber.d("Great! This response contains next!! next: $next")
          }
        }
        callback(it.body()!!, next)
      }, {
        Timber.e(it)
      })
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
    // do nothing
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
    getList(userUseCase.user?.login?: "", params.key) { events, next ->
      Timber.d("next: $next")
      callback.onResult(events, next)
    }
  }

  override fun loadInitial(params: LoadInitialParams<Int>,
                           callback: LoadInitialCallback<Int, Event>) {
    getList(userUseCase.user?.login?: "", 1) { events, next ->
      callback.onResult(events, null, next)
    }
  }
}