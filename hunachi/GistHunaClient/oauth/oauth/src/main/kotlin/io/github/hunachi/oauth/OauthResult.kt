package io.github.hunachi.oauth

import androidx.lifecycle.LiveData
import io.github.hunachi.oauthnetwork.model.Token
import io.github.hunachi.shared.network.NetWorkError

data class OauthResult(
        val tokenState: LiveData<Token>,
        val errorState: LiveData<NetWorkError>,
        val loadingState: LiveData<Boolean>
)