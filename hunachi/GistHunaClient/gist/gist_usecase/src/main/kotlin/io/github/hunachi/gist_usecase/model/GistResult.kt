package io.github.hunachi.gist_usecase.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.network.NetWorkError

data class GistResult(
        val gistsState: LiveData<PagedList<Gist>>,
        val loadingState: LiveData<Boolean>,
        val errorState: LiveData<NetWorkError>
)