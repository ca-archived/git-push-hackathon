package io.github.hunachi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.hunachi.gistlocal.dao.FileDao
import io.github.hunachi.gistlocal.dao.GistDao
import io.github.hunachi.model.File
import io.github.hunachi.model.Gist

@Database(
        entities = [Gist::class, File::class],
        version = 1,
        exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getGistDao(): GistDao

    abstract fun getFileDao(): FileDao

    companion object {
        val DATABASE_NAME = "Gist1.db"

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: gistDatabase(context).also { INSTANCE = it }
        }

        private fun gistDatabase(context: Context) =
            Room.databaseBuilder<MyDatabase>(
                    context.applicationContext,
                    MyDatabase::class.java,
                    DATABASE_NAME
            ).build()
    }
}