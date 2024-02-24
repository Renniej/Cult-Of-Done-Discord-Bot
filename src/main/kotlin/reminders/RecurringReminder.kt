package reminders.reminders

import Reminder
import kotlinx.datetime.Instant
import reminders.ReminderListener
import reminders.rennie.IReminder
import kotlin.time.Duration

//TODO restudy this code because jesus christ you understand none of it LOL. Actually lets do a documentation session rn
class RecurringReminder(title : String, desc : String, date : Instant, val recurringDuration: Duration) : Reminder(title,desc,date) {

    private var curReminder = Reminder(title,desc,date)
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
        curReminder.setEventListener(curReminderListener)
        curReminder.start()
    }

    private fun createNextReminder() : Reminder = Reminder(curReminder.title, curReminder.desc, curReminder.time.plus(recurringDuration))
    private fun onCurReminderFired(reminder: IReminder) {
        super.fireReminder()
        curReminder = createNextReminder()
        startCurReminder()
    }

    override fun started(): Boolean = curReminder.started()

}