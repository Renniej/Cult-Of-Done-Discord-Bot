package rennie

import ReminderManager
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


fun main() {

    val manager = ReminderManager()
    val title  : String
    val desc : String
    val date : Instant

    println("Enter reminder title")
    title = readln()
    println("Enter reminder description")
    desc = readln()
    date = Clock.System.now()

    manager.addReminder(title, desc, date)

    println(manager.toString())

}