package reminders.diskord_reminders

import ReminderManager
import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.util.sendMessage
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import reminders.rennie.IReminder


fun embedRemainder(rem : IReminder) = Embed(rem.title,rem.desc, timestamp = rem.time.toString())

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
             channel.sendMessage(embeds = arrayOf(embedRemainder(reminder)))
         }
    }
    fun addReminder(title : String, desc : String, time : Instant)  = manager.addReminder(title,desc,time)


}
