package reminders

import ReminderManager
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.BaseVoiceChannelBehavior
import io.ktor.network.sockets.*

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import reminders.diskord_reminders.DiscordRemainderManager
import reminders.diskord_reminders.bindRemind

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

    //manager.addReminder(title, desc, date)
}



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

    val botToken = args[0]
    val channelId = "1209964221253812334"
    val manager = DiscordRemainderManager(botToken,channelId)

    val kord= Kord(botToken)

    kord.login()



}