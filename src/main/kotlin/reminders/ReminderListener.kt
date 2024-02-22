package reminders

import reminders.rennie.IReminder


class ReminderListener {

    private var onFired : ((IReminder) -> Unit)? = null


    fun fireReminder(reminder : IReminder) {
        onReminderFired(reminder)
    }
    private fun onReminderFired(reminder : IReminder) = onFired?.let { it(reminder) }

    fun setOnReminderFired(f : (IReminder) -> Unit) {
        onFired = f
    }

}