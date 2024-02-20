package rennie.rennie

import kotlinx.datetime.Instant

interface IReminder {
    val title : String
    val desc : String
    val time : Instant


}