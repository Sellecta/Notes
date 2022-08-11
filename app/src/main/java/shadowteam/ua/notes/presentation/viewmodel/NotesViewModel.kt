package shadowteam.ua.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import shadowteam.ua.notes.domain.usecase.AddNotesUseCase
import shadowteam.ua.notes.domain.usecase.DeleteNotesUseCase
import shadowteam.ua.notes.domain.usecase.GetNotesListUseCase
import shadowteam.ua.notes.domain.usecase.LoadDataUseCase
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val getNotesListUseCase: GetNotesListUseCase,
    private val addNotesUseCase: AddNotesUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val loadDataUseCase: LoadDataUseCase
) :ViewModel(){
    val liveListNotes = getNotesListUseCase()

    val liveWorkerState = loadDataUseCase()

    fun addNotes(){
        viewModelScope.launch {
            addNotesUseCase.invoke()
        }
    }

    fun delNotes(id: Int){
        viewModelScope.launch { deleteNotesUseCase(id) }

    }
}