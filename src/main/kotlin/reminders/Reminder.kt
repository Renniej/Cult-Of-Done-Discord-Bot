
import kotlinx.datetime.*
import reminders.rennie.IReminder
import reminders.ReminderListener
import java.util.EventListener
import java.util.Timer
import kotlin.concurrent.timerTask
import kotlin.time.Duration


open class Reminder(override val title : String, override val desc : String, override val time : Instant,  start: Boolean = false) : IReminder  {

    private val timer = Timer()
    private var eventListener : ReminderListener? = null
    private var started = false

    init {
        if (start) {
            start()
        }
    }



    open fun start() {
        if (started) throw Exception("This Reminder [$title] has already been started")

        val dur : Duration = time.minus(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toInstant(TimeZone.UTC))

        if (dur.isNegative())  throw Exception("date given is in the past: $dur")

        timer.schedule(timerTask {fireReminder()
        }, dur.inWholeMilliseconds)
    }

    open fun started() : Boolean = started
    fun setEventListener(listener  : ReminderListener) {
        eventListener = listener
    }

     protected open fun fireReminder(){
        eventListener?.fireReminder(this)
    }

    override fun toString(): String {
        return "$title, $desc, $time"
    }

}


