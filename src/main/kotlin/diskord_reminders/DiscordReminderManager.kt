package reminders.diskord_reminders

import Reminder
import ReminderManager
import com.jessecorbett.diskord.api.channel.ChannelClient
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.api.channel.Embed
import com.jessecorbett.diskord.util.sendMessage
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import reminders.rennie.IReminder
import java.time.ZoneId
import kotlin.time.Duration.Companion.days



class DiscordRemainderManager(token : String, channelId: String) : ReminderManager() {

    private val http = RestClient.default(token)
    private val channel = ChannelClient(channelId, http)

    init {
        setOnReminderFired { reminder ->
            this.onReminderFired(reminder)
        }
    }

    override fun addReminder(reminder : Reminder)  = super.addReminder(reminder.toDiscordReminder())
    fun addReminder(discordReminder: DiscordReminder) = super.addReminder(discordReminder)

    override fun onReminderFired(reminder : IReminder) {
         runBlocking {
             val discordReminder = reminder as DiscordReminder
             val embed = discordReminder.embed()
             channel.sendMessage(embeds = arrayOf(embed))
         }
    }

}
