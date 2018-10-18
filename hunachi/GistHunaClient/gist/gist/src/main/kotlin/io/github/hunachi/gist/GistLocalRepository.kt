package io.github.hunachi.gist

import androidx.paging.DataSource
import io.github.hunachi.gistlocal.GistDatabase
import io.github.hunachi.model.File
import io.github.hunachi.model.Gist
import java.util.concurrent.Executor

class GistLocalRepository(
        private val database: GistDatabase,
        private val executor: Executor
) {

    fun gists(): DataSource.Factory<Int, Gist> = database.getGistDao().findGists()

    fun insertGists(gists: List<Gist>, finished: () -> Unit) {
        executor.execute {
            database.getGistDao().insertGists(gists)
            finished()
        }
    }

    fun insertFiles(files: List<File>, finished: () -> Unit) {
        executor.execute {
            database.getFileDao().insertFiles(files)
            finished()
        }
    }
}