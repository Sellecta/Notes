package shadowteam.ua.notes.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import shadowteam.ua.notes.R
import shadowteam.ua.notes.data.database.dao.NotesDao
import shadowteam.ua.notes.data.mapper.NotesMapper
import shadowteam.ua.notes.domain.dataclass.Notes
import shadowteam.ua.notes.domain.domaininterface.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val mapper: NotesMapper,
    private val application: Application,
    private val notesDao: NotesDao
) : NotesRepository {
    override fun loadData() {
        TODO("Not yet implemented")
    }

    override fun getNotesList(): LiveData<List<Notes>> {
      return Transformations.map(notesDao.getAllNotes()){ list ->
          list.map {
              mapper.mapNotesDbToEntity(it)
          }
      }
    }

    override fun addNotes() {
        val notes = Notes(
            AUTOGENERATE_ID,
            application.getString(R.string.default_title),
            "",
            "13:24")
        notesDao.insertNotes(mapper.mapNotesToNotesDb(notes))
    }

    override fun deleteNotes(id: Int) {
        notesDao.deleteNotes(id)
    }

    override fun getEditNotes(id: Int, title: String, desc: String) {
        val notes = notesDao.getNotesItem(id)
        val newNotes = notes.copy(title = title, description = desc)
        notesDao.insertNotes(newNotes)
    }

    override fun getNotes(id: Int): Notes {
        return mapper.mapNotesDbToEntity(notesDao.getNotesItem(id))
    }


    companion object{
        private const val AUTOGENERATE_ID = 0
    }
}