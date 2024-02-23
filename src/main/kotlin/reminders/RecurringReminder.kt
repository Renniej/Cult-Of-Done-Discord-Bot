package reminders.reminders

import Reminder
import kotlinx.datetime.Instant
import reminders.ReminderListener
import reminders.rennie.IReminder
import kotlin.time.Duration


class RecurringReminder(t : String, d : String, date : Instant, val recurringDuration: Duration) : Reminder(t,d,date) {

    private var curReminder = Reminder(t,d,date)
    private val curReminderListener = ReminderListener()

    init {
        curReminderListener.setOnReminderFired(this::onCurReminderFired)
    }

    override val title : String
        get() = curReminder.title
    override val  desc : String
        get() = curReminder.desc
    override val time : Instant
        get() = curReminder.time


    override fun start() = startCurReminder()

    private fun startCurReminder() {
        curReminder.start()
        curReminder.setEventListener(curReminderListener)
    }

    private fun createNextReminder() : Reminder = Reminder(curReminder.title, curReminder.desc, curReminder.time.plus(recurringDuration))
    private fun onCurReminderFired(reminder: IReminder) {
        super.fireReminder()
        curReminder = createNextReminder()
        startCurReminder()
    }

    override fun started(): Boolean = curReminder.started()

}