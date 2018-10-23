package io.github.hunachi.oauth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.github.hunachi.model.User
import io.github.hunachi.oauthnetwork.model.Token
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

internal class OAuthStore(dispatcher: Dispatcher) : Store() {

    private val oauthSubscriber = dispatcher.asChannel<OauthAction>()
    private var job: Job? = null

    val _oathUrlState = MutableLiveData<String>()
    val oauthUrlState: LiveData<String> = _oathUrlState

    private val _oauthResultState = MutableLiveData<OauthResult>()

    val isLoadingState: LiveData<Boolean> = Transformations.switchMap(_oauthResultState) {
        it.loadingState
    }

    val tokenState: LiveData<Token> = Transformations.switchMap(_oauthResultState) {
        it.tokenState
    }

    private val _errorState = MediatorLiveData<NetWorkError>()
    val errorState: LiveData<NetWorkError> = _errorState

    private val _userResultState = MutableLiveData<UserResult>()

    val userState: LiveData<User> = Transformations.switchMap(_userResultState) {
        it.userState
    }

    override fun onCreate() {
        job = CoroutineScope(Dispatchers.Main).launch {
            oauthSubscriber.consumeEach {
                when (it) {
                    is OauthAction.IgniteOauth -> _oathUrlState.value = it.url

                    is OauthAction.ReceiveOauthResult -> {
                        _oauthResultState.postValue(it.oauthResult)
                        addErrorState(it.oauthResult.errorState)
                    }

                    is OauthAction.UpdateUser -> {
                        _userResultState.value = it.userResult
                        addErrorState(it.userResult.errorState)
                    }
                }.checkAllMatched
            }
        }
    }

    override fun onCleared() {
        oauthSubscriber.cancel()
        job?.cancel()
        super.onCleared()
    }

    private fun addErrorState(errorState: LiveData<NetWorkError>) {
        _errorState.addSource(errorState) { if (it != null) _errorState.value = it }
    }
}
