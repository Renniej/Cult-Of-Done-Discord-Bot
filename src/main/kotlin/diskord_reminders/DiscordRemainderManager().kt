package reminders.diskord_reminders

import Reminder
import ReminderManager
import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.sendMessage
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import reminders.rennie.IReminder
import kotlin.concurrent.thread


class DiscordRemainderManager(token : String, channelId: String) {

    private val http = RestClient.default(token)
    private val channel = ChannelClient(channelId, http)
    private val manager = ReminderManager()

    init {
        manager.setOnReminderFired { reminder ->
            this.onReminderFired(reminder)
        }
    }

    private  fun onReminderFired(reminder : IReminder) {
         runBlocking {
             channel.sendMessage(reminder.toString())
         }
    }
    fun addReminder(title : String, desc : String, time : Instant)  = manager.addReminder(title,desc,time)


}
