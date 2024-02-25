package reminders.diskord_reminders

import Reminder
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.bot.interaction.InteractionBuilder
import kotlinx.datetime.*
import reminders.reminders.RecurringReminder
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

private val MIN_RECURRING = 5.seconds
private val MAX_RECURRING = 365.days

private val RECURRING_RANGE = MIN_RECURRING..MAX_RECURRING // A reminder can only be printing for a min of


//accepts a string for date anda  string for time then attempts to parse it into an instant.  Returns null if string is formatted wrong
private fun parseDate(date : String, time : String = "00:00") : Instant? {

   val dt = "${date}T${time}"
   val timeZone = TimeZone.currentSystemDefault()



   return try {
       LocalDateTime.parse(dt).toInstant(timeZone)
    } catch (e : Exception) {
        null
    }

}


fun Instant.isIntTheFuture() : Boolean = this > Clock.System.now()


fun isValidDuration(d : Duration) : Boolean {
    return (d > Duration.ZERO && d in RECURRING_RANGE )
}
fun InteractionBuilder.bindRemind(manager : DiscordRemainderManager) {

    slashCommand("reminder", "sets reminder") {
        val title by stringParameter("title", "reminder title", optional = false)
        val desc by stringParameter("description" ,"reminder description", optional = false)
        val time by stringParameter("time", "must be in military time (3pm = 15:00). Default time is midnight",  optional = false)
        val recurInterval by stringParameter("recurring", " [ OPTIONAL ] interval that reminder should repeat. Input Examples : \"1h 30m 10s\" , \"1.5h\" , \"3d\"", optional = true)

        val date by stringParameter("date"," +5days or [YYYY-MM-DD] Default is today's date", optional = true)


        callback {

            val instant : Instant? = parseDate(date ?: LocalDate.now().toString() ,time!!)

            val recDuration : Duration? = try {
                Duration.parse(recurInterval ?: "invalid")
            }  catch (e :Exception) {
                null
            }

            val response : String =  when {
                recurInterval != null && recDuration == null -> "wrong recurring time format"
                recDuration != null && isValidDuration(recDuration)-> "recurring duration cannot be 0 or lower"
                instant == null -> "Invalid time format ):"
                !instant.isIntTheFuture() -> "reminder was set for the past ):"
                else -> {

                    val reminder = if (recDuration != null )
                        RecurringReminder(title!!,desc!!, instant, recurringDuration = recDuration)
                    else
                        Reminder(title!!,desc!!, instant)


                    manager.addReminder(reminder.toDiscordReminder())
                    "Reminder Added :)"
                }
            }


            respond {
                embeds = listOf(Embed(response))
            }

        }
    }




}