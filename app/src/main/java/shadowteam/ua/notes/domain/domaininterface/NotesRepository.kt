package shadowteam.ua.notes.domain.domaininterface

import androidx.lifecycle.LiveData
import shadowteam.ua.notes.domain.dataclass.Notes

interface NotesRepository {

    fun loadData()

    fun getNotesList():LiveData<List<Notes>>

    fun addNotes()

    fun deleteNotes(id: Int)

    fun getEditNotes(id: Int, title:String, desc:String)

    fun getNotes(id:Int):Notes
}