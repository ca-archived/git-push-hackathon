package io.github.hunachi.gist_usecase.model

import androidx.lifecycle.LiveData
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.network.NetWorkError

data class GistPostResult(
        val resultGistState: LiveData<Gist>,
        val errorState: LiveData<NetWorkError>,
        val loadingState: LiveData<Boolean>
)