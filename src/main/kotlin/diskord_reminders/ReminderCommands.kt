package reminders.diskord_reminders

import Reminder
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.rest.builder.interaction.string

import isIntTheFuture
import kotlinx.datetime.*
import parseDateTime
import reminders.reminders.RecurringReminder
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds

private val MIN_RECURRING = 5.seconds
private val MAX_RECURRING = 365.days

private val RECURRING_RANGE = MAX_RECURRING..MIN_RECURRING// A reminder can only be printing for a min of







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

suspend fun Kord.bindRemind(manager : DiscordRemainderManager) {



    createGlobalChatInputCommand(name = "reminder", description = "sets reminder") {

        string("title", "title of reminder") { required = true }
        string("description", "description of rmeinder") {required = true}
        string("time", "must be in military time (3pm = 15:00). Default time is midnight") {required = true}

        string("recurring"," [ OPTIONAL ] interval that reminder should repeat. Input Examples : \"1h 30m 10s\" , \"1.5h\" , \"3d\"", )
        string("date", "+5days or [YYYY-MM-DD] Default is today's date")

    }


    slashCommand("reminder", "sets reminder") {

        val title by stringParameter("title", "reminder title", optional = false)
        val desc by stringParameter("description" ,"reminder description", optional = false)
        val time by stringParameter("time", "must be in military time (3pm = 15:00). Default time is midnight",  optional = false)
        val recurInterval by stringParameter("recurring", " [ OPTIONAL ] interval that reminder should repeat. Input Examples : \"1h 30m 10s\" , \"1.5h\" , \"3d\"", optional = true)
        val date by stringParameter("date"," +5days or [YYYY-MM-DD] Default is today's date", optional = true)


        callback {

            val dateStr = date ?: LocalDate.now().toString()
            val timeStr = time ?: "00:00"

            val instant : Instant? = parseDateTime(dateStr ,timeStr) //date and time reminder should pop off

            val recDuration : Duration? = try { //at which interval a reminder should pop off if it is recurring
                Duration.parse(recurInterval ?: "invalid")
            }  catch (e :Exception) {
                null
            }

            val response : String = hasError(instant,recurInterval,recDuration) ?: addReminder(manager, title!!, desc!!,instant!!,recDuration) //discord ensures these are passed before sending it

            respond {
                embeds = listOf(Embed(response))
            }

        }
    }




}