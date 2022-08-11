package shadowteam.ua.notes.domain.usecase

import shadowteam.ua.notes.domain.domaininterface.NotesRepository
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: Int){
        repository.deleteNotes(id)
    }
}