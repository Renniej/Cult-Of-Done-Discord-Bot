package reminders

import ReminderManager
import kotlinx.datetime.Clock
import reminders.diskord_reminders.DiscordReminder
import reminders.reminders.RecurringReminder
import kotlin.time.Duration.Companion.seconds

fun main() {

    println("program started")
    val listener = ReminderListener()
    val recur = RecurringReminder("test","testing",Clock.System.now().plus(5.seconds), 5.seconds)
    val discordReminder = DiscordReminder(recur)

    val manager = ReminderManager()
    manager.setOnReminderFired { reminder -> println(reminder)  }
    manager.addReminder(discordReminder)


    println("Recurring reminder started")

}