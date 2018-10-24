package io.github.hunachi.oauth_usecase

import androidx.lifecycle.MutableLiveData
import io.github.hunachi.oauth_infra.BuildConfig
import io.github.hunachi.oauth_infra.OauthClient
import io.github.hunachi.oauth_infra.model.Token
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

internal class OauthUseCaseImpl(
        private val oauthClient: OauthClient,
        private val url: String
) : OauthUseCase {

    private val _tokenState = MutableLiveData<String>()
    private val _errorState = MutableLiveData<NetWorkError>()
    private val _loadingState = MutableLiveData<Boolean>()

    override fun register(code: String): OauthResult {
        _loadingState.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token: Token = runBlocking {
                    oauthClient.accessToken(
                            BuildConfig.CLIENT_ID,
                            BuildConfig.CLIENT_SECRET,
                            code
                    ).await()
                }
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