package io.github.hunachi.oauth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.github.hunachi.model.User
import io.github.hunachi.shared.*
import io.github.hunachi.shared.flux.Dispatcher
import io.github.hunachi.shared.flux.Store
import io.github.hunachi.shared.network.NetWorkError
import io.github.hunachi.user.UserResult
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

    private val _isErrorState = MutableLiveData<NetWorkError>()/*MediatorLiveData<NetWorkError>().apply {
        addSource(userStateError) { if (it != null) call() }
    }*/
    val isErrorState: LiveData<NetWorkError> = _isErrorState

    private val _isSuccessState = MutableLiveData<Nothing>()
    val isSuccessState = _isSuccessState

    private val _userResultState = MutableLiveData<UserResult>()

    val userState: LiveData<User> = Transformations.switchMap(_userResultState) {
        it.userState
    }

    private val userStateError: LiveData<NetWorkError> = Transformations.switchMap(_userResultState) {
        it.errorState
    }

    fun onCreate() {
        job = CoroutineScope(Dispatchers.Main).launch {
            oauthSubscriber.consumeEach {
                when (it) {
                    is OAuthAction.IgniteOauth -> _oathUrlState.value = it.url

                    is OAuthAction.SaveToken -> {
                        preference.token(it.token)
                        _isSuccessState.call()
                    }

                    is OAuthAction.IsLoading -> _isLoadingState.value = it.isLoading

                    is OAuthAction.IsError -> _isErrorState.value = NetWorkError.NORMAL

                    is OAuthAction.UpdateUser -> _userResultState.value = it.userResult
                }.checkAllMatched
            }
        }
    }

    override fun onCleared() {
        oauthSubscriber.cancel()
        job?.cancel()
        super.onCleared()
    }
}
