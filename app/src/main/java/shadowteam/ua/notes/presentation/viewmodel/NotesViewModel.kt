package shadowteam.ua.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
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
        addNotesUseCase.invoke()
    }

    fun delNotes(id: Int){
        deleteNotesUseCase(id)
    }
}