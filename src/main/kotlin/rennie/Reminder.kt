import kotlinx.datetime.Instant

open class Reminder(val title : String, val desc : String, val date : Instant) {
    override fun toString(): String {
        return "$title, $desc, $date"
    }
}


