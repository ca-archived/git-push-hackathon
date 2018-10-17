package io.github.hunachi.gist.data.local.converter

import androidx.room.TypeConverter
import java.lang.Math.max

class IdListConverter {
    @TypeConverter
    fun ListToString(list: List<Int>): String = list.toString()

    @TypeConverter
    fun StringToList(string: String): List<Int> = string
            .substring(1, max(string.lastIndex - 1, 1))
            .split(",")
            .map { it.trim().toInt() }
}