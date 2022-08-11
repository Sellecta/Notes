package shadowteam.ua.notes.domain.usecase

import shadowteam.ua.notes.domain.dataclass.Notes
import shadowteam.ua.notes.domain.domaininterface.NotesRepository
import javax.inject.Inject

class AddNotesUseCase @Inject constructor(
    private val repository: NotesRepository) {

    suspend operator  fun invoke(){
        repository.addNotes()
    }
}