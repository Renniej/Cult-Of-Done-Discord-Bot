package rennie

import ReminderManager
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.minutes


fun inputDate() : Instant {

    var minutes : Int? = null

    do {
        println("Input number of minutes later you'd like to recieve this notificaiton")


        minutes = readln().toIntOrNull()

        when {
            minutes == null -> println("Input was not a valid number")
            minutes < 1 -> println("number must be greater than 1")

        }
    } while (minutes == null || minutes < 1)



    return Clock.System.now().plus(minutes.minutes)

}


fun main() {

    val manager = ReminderManager()
    val title  : String
    val desc : String
    val date : Instant

    println("Enter reminder title")
    title = readln()
    println("Enter reminder description")
    desc = readln()
    date = inputDate()


    manager.addReminder(title, desc, date)

    println(manager.toString())

}