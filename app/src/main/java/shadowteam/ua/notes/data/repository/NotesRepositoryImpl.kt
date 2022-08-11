package shadowteam.ua.notes.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import shadowteam.ua.notes.R
import shadowteam.ua.notes.data.database.dao.NotesDao
import shadowteam.ua.notes.data.mapper.NotesMapper
import shadowteam.ua.notes.data.worker.LoadDataWorker
import shadowteam.ua.notes.domain.dataclass.Notes
import shadowteam.ua.notes.domain.domaininterface.NotesRepository
import java.util.*
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val mapper: NotesMapper,
    private val application: Application,
    private val notesDao: NotesDao
) : NotesRepository {

    override fun loadData(): LiveData<WorkInfo> {
        val workerRequest = LoadDataWorker.makeRequest()
        val worker = WorkManager.getInstance(application)
        worker.enqueueUniqueWork(
            LoadDataWorker.NAME_WORKER,
            ExistingWorkPolicy.REPLACE,
            workerRequest
        )
        return worker.getWorkInfoByIdLiveData(workerRequest.id)
    }


    override fun getNotesList(): LiveData<List<Notes>> {
      return Transformations.map(notesDao.getAllNotes()){ list ->
          list.map {
              mapper.mapNotesDbToEntity(it)
          }
      }
    }

    override suspend fun addNotes() {
        val notes = Notes(
            AUTOGENERATE_ID,
            application.getString(R.string.default_title),
            "",
            System.currentTimeMillis())
        notesDao.insertNotes(mapper.mapNotesToNotesDb(notes))
    }

    override suspend fun deleteNotes(id: Int) {
        notesDao.deleteNotes(id)
    }

    override suspend fun getEditNotes(id: Int, title: String, desc: String) {
        val notes = notesDao.getNotesItem(id)
        if(notes.title != title || notes.description !=desc){
            val newNotes =
                notes.copy(title = title, description = desc, data = System.currentTimeMillis())
            notesDao.insertNotes(newNotes)
        }
    }

    override suspend fun getNotes(id: Int): Notes {
        return mapper.mapNotesDbToEntity(notesDao.getNotesItem(id))
    }


    companion object{
        private const val AUTOGENERATE_ID = 0
    }
}