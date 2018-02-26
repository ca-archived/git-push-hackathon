package com.dev.touyou.gitme.Extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by touyou on 2018/02/26.
 */

val Date.yearsFrom: Int
    get() {
        val diff = this.compareTo(Date())
        return 0
    }

val Date.offsetString: String
    get() {

        if (yearsFrom > 0) {

            return "$yearsFrom years ago"
        }

        return "now"
    }

val Date.dateString: String
    get() {

        return SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN).format(this)
    }