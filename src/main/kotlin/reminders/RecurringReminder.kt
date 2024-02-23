package reminders.reminders

import Reminder
import kotlinx.datetime.Instant
import reminders.rennie.IReminder

class RecurringReminder(title : String, description : String, date : Instant) : Reminder(title,description,date) {

    private var curReminder = Reminder(title,description,date)
    override val title : String
        get() = curReminder.title
    override val  desc : String
        get() = curReminder.desc
    override val time : Instant
        get() = curReminder.time



    override fun start() {
        curReminder.start()
    }

    override fun started(): Boolean = curReminder.started()


}