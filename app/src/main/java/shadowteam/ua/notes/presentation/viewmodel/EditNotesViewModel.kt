package shadowteam.ua.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import shadowteam.ua.notes.data.database.dao.NotesDao
import shadowteam.ua.notes.domain.dataclass.Notes
import shadowteam.ua.notes.domain.usecase.GetNotesEditUseCase
import shadowteam.ua.notes.domain.usecase.GetNotesUseCase
import javax.inject.Inject

class EditNotesViewModel @Inject constructor(
    private val getNotesEditUseCase: GetNotesEditUseCase,
    private val getNotesUseCase: GetNotesUseCase
): ViewModel() {

    private val _liveNotes = MutableLiveData<Notes>()
    val liveNotes: LiveData<Notes>
        get() = _liveNotes



    fun getNotes(id:Int){
        _liveNotes.value = getNotesUseCase(id)
    }

    fun saveEditNotes(title: String, desc:String, id:Int){
        val notes = getNotesEditUseCase(id,title,desc)
    }
}