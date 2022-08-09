package shadowteam.ua.notes.domain.usecase

import androidx.lifecycle.LiveData
import shadowteam.ua.notes.domain.dataclass.Notes
import shadowteam.ua.notes.domain.domaininterface.NotesRepository
import javax.inject.Inject

class GetNotesListUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(): LiveData<List<Notes>>{
        return repository.getNotesList()
    }
}