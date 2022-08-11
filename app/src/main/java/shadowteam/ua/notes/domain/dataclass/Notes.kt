package shadowteam.ua.notes.domain.dataclass

import java.util.*

data class Notes(
    val id: Int,
    val title: String,
    val description: String,
    val data: Long,
    val formatData:String? = null
)