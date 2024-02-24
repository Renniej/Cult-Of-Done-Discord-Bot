package reminders.diskord_reminders

import Reminder
import com.jessecorbett.diskord.api.channel.Embed
import kotlinx.datetime.Instant
import reminders.rennie.IReminder

class DiscordReminder(title  : String, desc : String, time : Instant) : Reminder(title,desc,time){

     fun embed() : Embed {
        return Embed(title,desc, timestamp = time.toString() ) //note: for timestamp discord automatically converts UTC time to the approperiate when rendering embedded for user
    }

}