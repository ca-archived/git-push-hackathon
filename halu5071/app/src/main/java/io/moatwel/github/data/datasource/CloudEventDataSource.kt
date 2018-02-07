package io.moatwel.github.data.datasource

import android.arch.paging.PageKeyedDataSource
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.domain.entity.event.Event
import io.reactivex.Observable
import javax.inject.Inject

class CloudEventDataSource @Inject constructor(
  private val api: EventApi
) : PageKeyedDataSource<Int, Event>(){

  fun getList(name: String, page: Int): Observable<List<Event>> {
    return api.getList(name, page)
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Event>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Event>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}