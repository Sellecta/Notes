package shadowteam.ua.notes.domain.usecase

import shadowteam.ua.notes.domain.domaininterface.NotesRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val repository: NotesRepository) {

    operator fun invoke(id:Int) = repository.getNotes(id)
}