package reminders

import Reminder
import ReminderManager
import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.classicCommands
import com.jessecorbett.diskord.bot.interaction.interactions

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import reminders.discordBot.Bot

import java.io.File
import kotlin.time.Duration
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


fun managerTest() {
    val manager = ReminderManager()

    println("Enter reminder title")
    val title  : String = readln()

    println("Enter reminder description")
    val desc : String = readln()

    val date : Instant = inputDate()

    manager.addReminder(title, desc, date)
}

val manager = ReminderManager()

/*

fun reminder() = commands("Test") {

    slash(name = "Reminder", description = "reminder") {
        execute(AnyArg("Title"), AnyArg("Description") ) {

            val (title, desc) = args

            manager.addReminder(title,desc,Clock.System.now().plus(5.seconds))

        }
    }
}
*/


suspend fun main(args : Array<String>) {

    val token = args[0]
    val manager = ReminderManager()

    bot(token) {
        interactions {
            slashCommand("reminder", "sets reminder") {
                val title by stringParameter("title", "reminder title", optional = false)
                val desc by stringParameter("description" ,"reminder description", optional = false)
                callback {

                    manager.addReminder(title!!,desc!!, Clock.System.now().plus(5.seconds))

                    respond {
                        content = "Reminder Added"
                    }
                }
            }

        }
    }




}