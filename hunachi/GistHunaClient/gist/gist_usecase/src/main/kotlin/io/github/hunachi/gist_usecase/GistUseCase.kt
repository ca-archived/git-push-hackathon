package io.github.hunachi.gist_usecase

import io.github.hunachi.gist_usecase.model.GistResult

interface GistUseCase{

    fun update(userName: String?, token: String?): GistResult
}