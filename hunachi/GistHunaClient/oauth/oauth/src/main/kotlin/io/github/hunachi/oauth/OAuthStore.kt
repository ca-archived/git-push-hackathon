package io.github.hunachi.oauth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.hunachi.shared.*
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.shared.flux.Store
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

internal class OAuthStore(
        private val dispatcher: Dispatcher,
        private val preference: SharedPreferences
) : Store() {

    private val oauthSubscriber = dispatcher.asChannel<OAuthAction>()
    private var job: Job? = null

    val _oathUrlState = MutableLiveData<String>()
    val oauthUrlState: LiveData<String> = _oathUrlState

    private val _isLoadingState = MutableLiveData<Boolean>()
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    private val _isErrorState = MutableLiveData<Nothing>()
    val isErrorState: LiveData<Nothing> = _isErrorState

    private val _isSuccessState = MutableLiveData<Nothing>()
    val isSuccessState = _isSuccessState

    fun onCreate() {
        job = CoroutineScope(Dispatchers.Main).launch {
            oauthSubscriber.consumeEach {
                when (it) {
                    is OAuthAction.IgniteOauth -> _oathUrlState.value = it.url

                    is OAuthAction.SaveToken -> {
                        preference.saveToken(it.token)
                        _isSuccessState.call()
                    }

                    is OAuthAction.IsLoading -> _isLoadingState.value = it.isLoading

                    is OAuthAction.IsError -> _isErrorState.call()
                }
            }
        }
    }

    override fun onCleared() {
        oauthSubscriber.cancel()
        job?.cancel()
        super.onCleared()
    }
}
