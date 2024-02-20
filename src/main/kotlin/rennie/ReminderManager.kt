import kotlinx.datetime.Instant
import rennie.rennie.ReminderListener


class ReminderManager {

    private val reminders = mutableListOf<Reminder>()
    private val listener = ReminderListener()

    fun addReminder(title : String, desc : String, date : Instant) {

        val reminder = Reminder(title, desc, date).apply {
            setListener(listener)
        }

        reminders += reminder
    }


    override fun toString(): String = reminders.joinToString()


}