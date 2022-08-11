package shadowteam.ua.notes.domain.domaininterface

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import shadowteam.ua.notes.domain.dataclass.Notes

interface NotesRepository {

    fun loadData():LiveData<WorkInfo>

    fun getNotesList():LiveData<List<Notes>>

    suspend fun addNotes()

    suspend fun deleteNotes(id: Int)

    suspend fun getEditNotes(id: Int, title:String, desc:String)

    suspend fun getNotes(id:Int):Notes
}