package rennie.rennie



class ReminderListener {

    private var onReminderFired : (IReminder) -> Unit = this::onReminderFired


    fun fireReminder(reminder : IReminder) {
        onReminderFired(reminder)
    }
    private fun onReminderFired(reminder : IReminder) = println(reminder)

    fun setOnReminderFired(f : (IReminder) -> Unit) {
        onReminderFired = f
    }

}