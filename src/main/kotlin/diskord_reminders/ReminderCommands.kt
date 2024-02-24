package reminders.diskord_reminders

import Reminder
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.bot.BotContext
import com.jessecorbett.diskord.bot.interaction.InteractionBuilder
import kotlinx.datetime.*
import java.time.LocalDate
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes


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


fun InteractionBuilder.bindRemind(manager : DiscordRemainderManager) {

    slashCommand("reminder", "sets reminder") {
        val title by stringParameter("title", "reminder title", optional = false)
        val desc by stringParameter("description" ,"reminder description", optional = false)
        val time by stringParameter("time", "must be in military time (3pm = 15:00). Default time is midnight",  optional = false)
        val date by stringParameter("date","[YYYY-MM-DD] Default is today's date", optional = true)

        callback {

            val instant : Instant? = parseDate(date ?: LocalDate.now().toString() ,time!!)


            val response : String =  when {
                instant == null -> "Invalid time format ):"
                !instant.isIntTheFuture() -> "reminder was set for the past ):"
                else -> {
                    manager.addReminder(Reminder(title!!,desc!!, instant))
                    "Reminder Added :)"
                }
            }


            respond {
                embeds = listOf(Embed(response))
            }

        }
    }




}