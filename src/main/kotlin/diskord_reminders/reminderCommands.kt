package reminders.diskord_reminders

import com.jessecorbett.diskord.bot.interaction.InteractionBuilder
import kotlinx.datetime.*
import java.time.LocalDate
import java.util.Date
import kotlin.time.Duration.Companion.seconds


//accepts a string for date anda  string for time then attempts to parse it into an instant.  Returns null if string is formatted wrong
private fun parseDate(date : String?, time : String? = "00:00") : Instant? {

   val dt = "${date ?: LocalDate.now()}T${time}"

   return try {
       LocalDateTime.parse(dt).toInstant(TimeZone.UTC)
    } catch (e : Exception) {
        null
    }

}


fun InteractionBuilder.bindRemind(manager : DiscordRemainderManager) {

    slashCommand("reminder", "sets reminder") {

        val title by stringParameter("title", "reminder title", optional = false)
        val desc by stringParameter("description" ,"reminder description", optional = false)
        val time by stringParameter("time", "must be in military time (3pm = 15:00). Default time is midnight",  optional = false)
        val startDate by stringParameter("date","[YYYY-MM-DD] Date the reminder should first appear. Default is today", optional = true)


        callback {

            val instant = parseDate(startDate,time)

            val response : String

            if (instant != null) {
                manager.addReminder(title!!,desc!!, instant)
                response = "Reminder Added :)"

            } else {
                response = "Invalid time format ):"
            }


            respond {
                content = response
            }

        }
    }




}