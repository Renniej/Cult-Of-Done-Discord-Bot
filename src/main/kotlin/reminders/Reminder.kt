
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import reminders.rennie.IReminder
import reminders.ReminderListener
import java.util.EventListener
import java.util.Timer
import kotlin.concurrent.timerTask
import kotlin.time.Duration


open class Reminder(override val title : String, override val desc : String, override val time : Instant) : IReminder  {

    private val timer = Timer()
    private var eventListener : ReminderListener? = null

    init {

        val dur : Duration = time.minus(Clock.System.now())

        if (dur.isNegative())  throw Exception("date given is in the past: $dur")

        timer.schedule(timerTask {fireReminder()
        }, dur.inWholeMilliseconds)
    }

    fun setEventListener(listener  : ReminderListener) {
        eventListener = listener
    }

    private fun fireReminder(){
        eventListener?.fireReminder(this)
    }

    override fun toString(): String {
        return "$title, $desc, $time"
    }

}


