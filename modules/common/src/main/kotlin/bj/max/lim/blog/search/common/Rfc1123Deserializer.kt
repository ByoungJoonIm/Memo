package bj.max.lim.blog.search.common

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Rfc1123Deserializer : JsonDeserializer<Instant>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Instant {
        return ZonedDateTime.parse(p!!.readValueAs(String::class.java), DateTimeFormatter.RFC_1123_DATE_TIME).toInstant()
    }
}
