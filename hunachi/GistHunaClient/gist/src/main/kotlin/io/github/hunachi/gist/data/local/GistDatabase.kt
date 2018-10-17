package io.github.hunachi.gist.data.local

import androidx.room.Database
import io.github.hunachi.gist.data.local.dao.DraftGistDao
import io.github.hunachi.gist.data.local.dao.FileDao
import io.github.hunachi.gist.data.local.dao.GistDao
import io.github.hunachi.gist.data.local.model.DraftGistEntity
import io.github.hunachi.gist.data.local.model.FileEntity
import io.github.hunachi.gist.data.local.model.GistEntity

@Database(
        entities = [GistEntity::class, DraftGistEntity::class, FileEntity::class],
        version = 1,
        exportSchema = false
)
abstract class GistDatabase {

    abstract fun getGistDao(): GistDao

    abstract fun getDraftGistDao(): DraftGistDao

    abstract fun getFileDao(): FileDao
}