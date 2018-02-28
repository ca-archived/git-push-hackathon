package masegi.sho.sharehub.data.api.helper

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import masegi.sho.sharehub.data.model.event.Event.EventType

/**
 * Created by masegi on 2018/02/26.
 */

class EventTypeAdapter : JsonAdapter<EventType>() {

    override fun fromJson(reader: JsonReader?): EventType? {

        return if (reader != null) {

            if (reader.peek() != JsonReader.Token.NULL) {

                val typeString = reader.nextString()
                convertFromJson(typeString)
            }
            else {

                reader.nextNull()
            }
        }
        else null
    }


    override fun toJson(writer: JsonWriter, value: EventType?) {

        if (value == null) {

            writer.nullValue()
        }
        else {

            writer.value(convertToJson(value))
        }
    }


    companion object {

        private fun convertFromJson(jsonString: String): EventType? {

            EventType.values().forEach { type ->

                val comparedWith = type.toString() + "Event"
                if (comparedWith == jsonString)
                    return type
            }
            return if (jsonString == "PublicEvent") EventType.PUBLIC_EVENT
            else null
        }

        private fun convertToJson(value: EventType?): String {

            return ""
        }
    }
}
