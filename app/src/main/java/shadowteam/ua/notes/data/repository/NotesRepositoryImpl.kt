package shadowteam.ua.notes.data.repository

import androidx.lifecycle.LiveData
import shadowteam.ua.notes.domain.dataclass.Notes
import shadowteam.ua.notes.domain.domaininterface.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor() :
    NotesRepository {
    override fun loadData() {
        TODO("Not yet implemented")
    }

    override fun getNotesList(): LiveData<List<Notes>> {
        TODO("Not yet implemented")
    }

    override fun addNotes(notes: Notes) {
        TODO("Not yet implemented")
    }

    override fun deleteNotes(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getEditNotes(id: Int) {
        TODO("Not yet implemented")
    }
}