package reminders.diskord_reminders

import Reminder
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.bot.interaction.InteractionBuilder
import kotlinx.datetime.*
import reminders.reminders.RecurringReminder
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds

private val MIN_RECURRING = 5.seconds
private val MAX_RECURRING = 365.days

private val RECURRING_RANGE = MAX_RECURRING..MIN_RECURRING// A reminder can only be printing for a min of


//accepts a string for date anda  string for time then attempts to parse it into an instant.  Returns null if string is formatted wrong


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
        "invalid"
    }
}

private fun parseDateTime(date : String, time : String = "00:00") : Instant? {

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


fun Instant.isIntTheFuture() : Boolean = this > Clock.System.now()


private fun isValidDuration(d : Duration) : Boolean {
    return (d > Duration.ZERO && d in RECURRING_RANGE )
}


private fun hasError(dateTime : Instant?, recurInterval : String?, recDuration: Duration? ) : String? {
    return when {
        recurInterval != null && recDuration == null -> "wrong recurring time format"
        recDuration != null && isValidDuration(recDuration)-> "recurring duration cannot be 0 or lower"
        dateTime == null -> "Invalid time format ):"
        !dateTime.isIntTheFuture() -> "reminder was set for the past ):"
        else -> null
    }
}

fun addReminder(manager: DiscordRemainderManager, title : String, desc : String, dateTime : Instant, recDuration: Duration?) : String {

    val reminder = if (recDuration != null )
        RecurringReminder(title,desc, dateTime, recurringDuration = recDuration)  //hasError function checks for all of these
    else
        Reminder(title,desc, dateTime)

    manager.addReminder(reminder.toDiscordReminder())

    return "Reminder Added :)"
}

fun InteractionBuilder.bindRemind(manager : DiscordRemainderManager) {

    slashCommand("reminder", "sets reminder") {
        val title by stringParameter("title", "reminder title", optional = false)
        val desc by stringParameter("description" ,"reminder description", optional = false)
        val time by stringParameter("time", "must be in military time (3pm = 15:00). Default time is midnight",  optional = false)
        val recurInterval by stringParameter("recurring", " [ OPTIONAL ] interval that reminder should repeat. Input Examples : \"1h 30m 10s\" , \"1.5h\" , \"3d\"", optional = true)
        val date by stringParameter("date"," +5days or [YYYY-MM-DD] Default is today's date", optional = true)


        callback {

            val instant : Instant? = parseDateTime(date ?: LocalDate.now().toString() ,time!!) //date and time reminder should pop off

            val recDuration : Duration? = try { //at which interval a reminder should pop off if it is recurring
                Duration.parse(recurInterval ?: "invalid")
            }  catch (e :Exception) {
                null
            }

            val response : String = hasError(instant,recurInterval,recDuration) ?: addReminder(manager, title!!, desc!!,instant!!,recDuration)

            respond {
                embeds = listOf(Embed(response))
            }

        }
    }




}