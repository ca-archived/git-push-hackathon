package io.github.hunachi.gist

import androidx.paging.DataSource
import io.github.hunachi.gistlocal.GistDatabase
import io.github.hunachi.model.File
import io.github.hunachi.model.Gist
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.SendChannel
import java.util.concurrent.Executor

class GistLocalRepository(private val database: GistDatabase) {

    fun gists(): DataSource.Factory<Int, Gist> = database.getGistDao().findGists()

    suspend fun insertGists(gists: List<Gist>) = coroutineScope {
        database.getGistDao().insertGists(gists)
    }

    suspend fun insertFiles(files: List<File>)  = coroutineScope {
        database.getFileDao().insertFiles(files)
    }
}