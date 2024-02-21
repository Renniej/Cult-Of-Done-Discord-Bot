import kotlinx.datetime.Instant
import reminders.rennie.IReminder
import reminders.ReminderListener


class ReminderManager {

    private val reminders = mutableListOf<Reminder>()
    private val listener = ReminderListener().apply {
        setOnReminderFired(this@ReminderManager::onReminderFired)
    }


    fun addReminder(title : String, desc : String, date : Instant)  {

        val reminder = Reminder(title, desc, date).apply {
            setEventListener(listener)
        }

        reminders += reminder

    }


    private fun onReminderFired(reminder : IReminder) {
        println(reminder)
    }


    override fun toString(): String = reminders.joinToString()


}