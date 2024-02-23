import kotlinx.datetime.Instant
import reminders.rennie.IReminder
import reminders.ReminderListener


open class ReminderManager {

    private val reminders = mutableListOf<Reminder>()
    protected var onFiredFunc : ((reminder : IReminder) -> Unit?)? = null


    private val listener = ReminderListener().apply {
        setOnReminderFired(this@ReminderManager::onReminderFired)
    }


    //Adds a reminder to the manager and automatically starts it
    fun addReminder(title : String, desc : String, date : Instant)  {

        val reminder = Reminder(title, desc, date).apply {
            setEventListener(listener)
        }

        reminders += reminder
        reminder.start()

    }


   open fun setOnReminderFired(func : (reminder : IReminder)->Unit) {
        onFiredFunc = func
    }
    private fun onReminderFired(reminder : IReminder) {
        this.onFiredFunc?.let { it(reminder) }
    }


    override fun toString(): String = reminders.joinToString()


}