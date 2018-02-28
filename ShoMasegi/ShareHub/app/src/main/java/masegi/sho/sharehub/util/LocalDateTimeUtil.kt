package masegi.sho.sharehub.util

import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

/**
 * Created by masegi on 2018/02/18.
 */

object LocalDateTimeUtil {

    @JvmStatic
    fun durationFromToday(to: LocalDateTime): String {

        val duration = Duration.between(to, LocalDateTime.now())

        return when {

            duration.toMinutes() <= 60 -> duration.toMinutes().toString() + " minutes ago"
            duration.toHours() <= 24 -> duration.toHours().toString() + " hours ago"
            duration.toDays() <= 7 -> duration.toDays().toString() + " days ago"
            else -> {

                val formatter = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH)
                "on " + to.format(formatter)
            }
        }
    }
}
