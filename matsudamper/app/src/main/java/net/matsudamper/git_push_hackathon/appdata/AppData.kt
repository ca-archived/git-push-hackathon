package net.matsudamper.git_push_hackathon.appdata

import android.content.Context
import androidx.content.edit

class AppData(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("appData", Context.MODE_PRIVATE)

    private val TOKEN = "token"
    var token
        get() = sharedPreferences.getString(TOKEN, null)
        set(value) = sharedPreferences.edit {
            putString(TOKEN, value)
        }

    private val NAME = "name"
    var name
        get() = sharedPreferences.getString(NAME, null)
        set(value) = sharedPreferences.edit {
            putString(NAME, value)
        }

    private val ID = "id"
    var id
        get() = sharedPreferences.getLong(ID, throw IllegalStateException())
        set(value) = sharedPreferences.edit {
            putLong(ID, value)
        }
}