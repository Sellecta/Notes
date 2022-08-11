package shadowteam.ua.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import shadowteam.ua.notes.data.database.dao.NotesDao
import shadowteam.ua.notes.domain.dataclass.Notes
import shadowteam.ua.notes.domain.usecase.GetNotesEditUseCase
import shadowteam.ua.notes.domain.usecase.GetNotesUseCase
import javax.inject.Inject

class EditNotesViewModel @Inject constructor(
    private val getNotesEditUseCase: GetNotesEditUseCase,
    private val getNotesUseCase: GetNotesUseCase
): ViewModel() {


    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _liveNotes = MutableLiveData<Notes>()
    val liveNotes: LiveData<Notes>
        get() = _liveNotes



    fun getNotes(id:Int){
        viewModelScope.launch {
           val notes = getNotesUseCase(id)
            _liveNotes.value = notes
        }

    }

    fun saveEditNotes(title: String, desc:String, id:Int){
        viewModelScope.launch {
            getNotesEditUseCase(id,title,desc)
            finishWork()
        }
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}