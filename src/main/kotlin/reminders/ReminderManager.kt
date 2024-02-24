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
    open fun addReminder(reminder : Reminder)  {

        reminder.setEventListener(listener)
        reminders += reminder

        reminder.start()
    }


   open fun setOnReminderFired(func : (reminder : IReminder)->Unit) {
        onFiredFunc = func
    }
    protected open fun onReminderFired(reminder : IReminder) {
        this.onFiredFunc?.let { it(reminder) }
    }


    override fun toString(): String = reminders.joinToString()


}