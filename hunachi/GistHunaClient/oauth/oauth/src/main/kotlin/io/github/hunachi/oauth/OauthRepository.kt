package io.github.hunachi.oauth

import androidx.lifecycle.MutableLiveData
import io.github.hunachi.oauthnetwork.BuildConfig
import io.github.hunachi.oauthnetwork.OauthClient
import io.github.hunachi.oauthnetwork.model.Token
import io.github.hunachi.shared.network.NetWorkError
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class OauthRepository(private val oauthClient: OauthClient, private val url: String) {

    companion object {
        const val STATE_CODE = "gist-hunachi"
    }

    private val _tokenState = MutableLiveData<Token>()
    private val _errorState = MutableLiveData<NetWorkError>()
    private val _loadingState = MutableLiveData<Boolean>()

    fun register(code: String): OauthResult {
        _loadingState.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                try {
                    val token = oauthClient.accessToken(
                            BuildConfig.CLIENT_ID,
                            BuildConfig.CLIENT_SECRET,
                            code
                    ).await()
                    _tokenState.postValue(token)
                } catch (e: Exception) {
                    _errorState.postValue(NetWorkError.TOKEN)
                }
            }.join()
            _loadingState.postValue(false)
        }
        return OauthResult(_tokenState, _errorState, _loadingState)
    }

    fun getOauthUrl() = url + "&state=" + STATE_CODE
}