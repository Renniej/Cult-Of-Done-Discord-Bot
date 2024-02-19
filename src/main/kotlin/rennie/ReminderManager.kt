import kotlinx.datetime.Instant


class ReminderManager {

    private val reminders = mutableListOf<Reminder>()

    fun addReminder(title : String, desc : String, date : Instant) {
        reminders += Reminder(title, desc, date)
    }


    override fun toString(): String = reminders.joinToString()


}