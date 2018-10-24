package io.github.hunachi.oauth_usecase

import androidx.lifecycle.LiveData
import io.github.hunachi.shared.network.NetWorkError

data class OauthResult(
        val tokenState: LiveData<String>,
        val errorState: LiveData<NetWorkError>,
        val loadingState: LiveData<Boolean>
)