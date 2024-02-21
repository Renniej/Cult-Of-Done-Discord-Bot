package reminders

import ReminderManager
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


fun inputDate() : Instant {

    var secs : Int? = null

    do {
        println("Input number of seconds later you'd like to recieve this notificaiton")


        secs = readln().toIntOrNull()

        when {
            secs == null -> println("Input was not a valid number")
            secs < 1 -> println("number must be greater than 1")

        }
    } while (secs == null || secs < 1)



    return Clock.System.now().plus(secs.seconds)

}


fun main() {

    val manager = ReminderManager()

    println("Enter reminder title")
    val title  : String = readln()

    println("Enter reminder description")
    val desc : String = readln()

    val date : Instant = inputDate()

    manager.addReminder(title, desc, date)

}