package reminders.diskord_reminders

import Reminder
import com.jessecorbett.diskord.api.channel.Embed
import kotlinx.datetime.Instant
import reminders.ReminderListener


fun Reminder.toDiscordReminder() : DiscordReminder = DiscordReminder(this)

class DiscordReminder(private val reminder: Reminder) : Reminder(reminder.title, reminder.desc, reminder.time) { //Could lead to a weird amount of memory for recurring reminders lol

    private val listener : ReminderListener = ReminderListener()
    override val title : String
        get() = reminder.title
    override val  desc : String
        get() = reminder.desc
    override val time : Instant
        get() = reminder.time


    init {
        listener.setOnReminderFired { super.fireReminder() }
    }
    override fun start() {
        reminder.setEventListener(listener)
        reminder.start()
    }


    fun embed() : Embed {
        return Embed(reminder.title,reminder.desc, timestamp = reminder.time.toString() ) //note: for timestamp discord automatically converts UTC time to the approperiate when rendering embedded for user
    }

}