import kotlinx.datetime.Instant
import rennie.rennie.IReminder
import rennie.rennie.ReminderListener


class ReminderManager {

    private val reminders = mutableListOf<Reminder>()
    private val listener = ReminderListener().apply {
        setOnReminderFired(this@ReminderManager::onReminderFired)
    }


    fun addReminder(title : String, desc : String, date : Instant) {

        val reminder = Reminder(title, desc, date).apply {
            setListener(listener)
        }

        reminders += reminder
    }


    private fun onReminderFired(reminder : IReminder) {
        println(reminder)
    }


    override fun toString(): String = reminders.joinToString()


}