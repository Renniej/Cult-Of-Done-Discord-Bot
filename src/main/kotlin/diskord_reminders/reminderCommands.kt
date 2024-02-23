package reminders.diskord_reminders

import com.jessecorbett.diskord.bot.interaction.InteractionBuilder
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.Date
import kotlin.time.Duration.Companion.seconds


//accepts a string and attempts to parse it into an instant.  Returns null if string is formatted wrong
private fun parseDate(str : Date) : Instant? {
    return null
}


fun InteractionBuilder.bindRemind(manager : DiscordRemainderManager){

    slashCommand("reminder", "sets reminder") {
        val title by stringParameter("title", "reminder title", optional = false)
        val desc by stringParameter("description" ,"reminder description", optional = false)
        //val startDate by stringParameter("date (YYYY-MM-DD)","Date the reminder should first appear")
        //val time by stringParameter("time", "must be in military time (3pm = 15:00)")





        callback {

            manager.addReminder(title!!,desc!!, Clock.System.now().plus(5.seconds))

            respond {
                content = "Reminder Added"
            }
        }
    }




}