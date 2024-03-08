import kotlinx.datetime.*
import kotlin.time.Duration

//accepts a string for date anda  string for time then attempts to parse it into an instant.  Returns null if string is formatted wrong

fun Instant.isIntTheFuture() : Boolean = this > Clock.System.now()

private fun parseDate(str : String, timeZone: TimeZone) : String {
    return try {
        val duration = Duration.parse(str.substringAfter('+'))
        Clock.System.now().plus(duration).toLocalDateTime(timeZone).date.toString()
    } catch (e : Exception) {
        "invalid"
    }
}

private fun parseTime(str : String,timeZone: TimeZone) : String{
    return try {
        val duration = Duration.parse(str.substringAfter('+'))
        Clock.System.now().plus(duration).toLocalDateTime(timeZone).time.toString()
    } catch (e : Exception) {
        "Invalid Time"
    }
}

fun parseDateTime(date : String, time : String) : Instant? {

    val timeZone = TimeZone.currentSystemDefault()

    val strDate =  if (date.first() == '+')
        parseDate(date,timeZone)
    else
        date


    val strTime = if (time.first() == '+')
        parseTime(time,timeZone)
    else
        time

    return try {
        LocalDateTime.parse("${strDate}T${strTime}").toInstant(timeZone)
    } catch (e : Exception) {
        null
    }

}
