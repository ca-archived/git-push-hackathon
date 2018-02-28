package masegi.sho.sharehub.data.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import masegi.sho.sharehub.data.api.GithubApi
import masegi.sho.sharehub.data.model.event.Event

/**
 * Created by masegi on 2018/02/17.
 */

class EventDataSourceFactory(private val api: GithubApi) : DataSource.Factory<Int, Event> {


    // MARK: - Property

    internal var sourceLiveData = MutableLiveData<PageKeyedEventDataSource>()


    // MARK: - DataSource.Factory

    override fun create(): DataSource<Int, Event> {

        val source = PageKeyedEventDataSource(api)
        sourceLiveData.postValue(source)
        return source
    }
}