package masegi.sho.sharehub.presentation.event

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import masegi.sho.sharehub.data.api.GithubApi
import masegi.sho.sharehub.data.repository.EventRepository
import javax.inject.Inject

/**
 * Created by masegi on 2018/02/14.
 */

class MainViewModel @Inject constructor(
        private val api: GithubApi
): ViewModel() {


    // MARK: - Property

    private val repository: EventRepository = EventRepository(api)
    internal val events = repository.events
    internal val initialLoad = repository.initialLoad
    internal val userHtmlUrl: MutableLiveData<String> = MutableLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    // MARK: - Internal

    fun refresh() {

        repository.refresh()
    }

    fun getUserInfo(username: String) {

        api.getUser(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {

                    userHtmlUrl.postValue(it.htmlUrl)
                }
                .addTo(compositeDisposable)
    }


    // MARK: - ViewModel

    override fun onCleared() {

        super.onCleared()
        compositeDisposable.clear()
    }
}
