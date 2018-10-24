package io.github.hunachi.oauth_usecase

import androidx.lifecycle.MutableLiveData
import io.github.hunachi.oauth_infra.OauthRepository
import io.github.hunachi.oauth_infra.model.Token
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

internal class OauthUseCaseImpl(
        private val oauthClient: OauthRepository,
        private val url: String
) : OauthUseCase {

    private val _tokenState = MutableLiveData<String>()
    private val _errorState = MutableLiveData<NetWorkError>()
    private val _loadingState = MutableLiveData<Boolean>()

    override fun register(code: String): OauthResult {
        _loadingState.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token: Token = oauthClient.accessToken(code)

                _tokenState.postValue(token.token)
            } catch (e: Exception) {
                _errorState.postValue(NetWorkError.TOKEN)
            }
            _loadingState.postValue(false)
        }
        return OauthResult(_tokenState, _errorState, _loadingState)
    }

    override fun getOauthUrl() = url + "&state=" + OauthUseCase.STATE_CODE
}