package io.github.hunachi.gistlocal

import android.content.Context
import androidx.room.Room
import io.github.hunachi.gist.data.local.GistDatabase

object GIstLocalAdapter {

    val DATABASE_NAME = "gist-database"

    fun gistDatabase(context: Context) = Room.databaseBuilder<GistDatabase>(
            context,
            GistDatabase::class.java,
            DATABASE_NAME
    )
}