package shadowteam.ua.notes.data.mapper

import android.app.Application
import shadowteam.ua.notes.R
import shadowteam.ua.notes.data.database.model.NotesDb
import shadowteam.ua.notes.domain.dataclass.Notes
import shadowteam.ua.notes.presentation.application.NotesApplication
import java.util.*
import javax.inject.Inject

class NotesMapper @Inject constructor(
    private val application: Application
) {

    fun mapNotesToNotesDb(notes: Notes):NotesDb{
        return NotesDb(
            id = notes.id,
            title = notes.title,
            description = notes.description,
            data = notes.data
        )
    }

    fun mapNotesDbToEntity(notesDb: NotesDb):Notes {
        return Notes(
            id = notesDb.id,
            title = notesDb.title,
            description = notesDb.description,
            data = notesDb.data,
            formatData = formatDataFromLong(notesDb.data)
        )
    }

    private fun formatDataFromLong(time:Long):String{
        val calendar = Calendar.getInstance()
        val calendarFromDb = Calendar.getInstance().apply { timeInMillis = time }
        val dayDb = calendarFromDb[Calendar.DAY_OF_MONTH]
        val monthDb = calendarFromDb[Calendar.MONTH]
        return if(dayDb == calendar[Calendar.DAY_OF_MONTH] && monthDb == calendar[Calendar.MONTH]){
            val template = application.getString(R.string.time_first_template)
            String.format(template,calendarFromDb[Calendar.HOUR_OF_DAY], calendarFromDb[Calendar.MINUTE])
        } else {
            val template = application.getString(R.string.time_two_template)
            String.format(template,dayDb,monthDb + 1,calendarFromDb[Calendar.YEAR])
        }
    }
}