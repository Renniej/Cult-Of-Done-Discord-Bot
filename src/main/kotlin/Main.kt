package reminders

import Reminder
import ReminderManager
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.CommandArgument
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.jakejmattson.discordkt.arguments.AnyArg
import me.jakejmattson.discordkt.arguments.IntegerArg
import me.jakejmattson.discordkt.arguments.StringArgument
import me.jakejmattson.discordkt.commands.CommandSetBuilder
import me.jakejmattson.discordkt.commands.commands
import me.jakejmattson.discordkt.dsl.Bot
import me.jakejmattson.discordkt.dsl.bot
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


fun reminder() = commands("Test") {

    slash(name = "Reminder", description = "reminder") {
        execute(AnyArg("Title"), AnyArg("Description") ) {

            val (title, desc) = args

            manager.addReminder(title,desc,Clock.System.now().plus(5.seconds))

        }
    }
}


fun main() {

    val file = File("C:\\Users\\Rennie\\IdeaProjects\\Cult-Of-Done-Discord-Bot\\src\\main\\botToken.txt")
    val token = file.readText()

    val bot : Bot

    bot(token) {
        prefix { "+" }

    }



}