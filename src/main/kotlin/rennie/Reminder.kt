import kotlinx.datetime.Instant
import rennie.rennie.IReminder
import rennie.rennie.ReminderListener


open class Reminder(override val title : String, override val desc : String, override val time : Instant) : IReminder {

    private var eventListener : ReminderListener? = null


    fun setListener(listener : ReminderListener){
        eventListener = listener
    }

    fun fireReminder(){
        eventListener?.onReminderFired(this)
    }

    override fun toString(): String {
        return "$title, $desc, $time"
    }

}


