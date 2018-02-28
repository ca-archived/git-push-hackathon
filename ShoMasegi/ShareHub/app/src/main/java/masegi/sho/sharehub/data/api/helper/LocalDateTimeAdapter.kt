package masegi.sho.sharehub.data.api.helper

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by masegi on 2018/02/18.
 */

class LocalDateTimeAdapter : JsonAdapter<LocalDateTime>() {

    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {

        if (value == null) {

            writer.nullValue()
        }
        else {

            writer.value(value.format(formatter))
        }
    }

    override fun fromJson(reader: JsonReader): LocalDateTime? =
        if (reader.peek() != JsonReader.Token.NULL) {

            val dateString = reader.nextString()
            LocalDateTime.parse(dateString, formatter)
        }
        else {

            reader.nextNull()
        }

    companion object {

        private val formatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }
}
