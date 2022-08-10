package shadowteam.ua.notes.data.mapper

import shadowteam.ua.notes.data.database.model.NotesDb
import shadowteam.ua.notes.domain.dataclass.Notes
import javax.inject.Inject

class NotesMapper @Inject constructor() {

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
            data = notesDb.data)
    }
}