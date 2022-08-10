package shadowteam.ua.notes.domain.usecase

import shadowteam.ua.notes.domain.domaininterface.NotesRepository
import javax.inject.Inject

class GetNotesEditUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    operator fun invoke(id: Int, title: String, desc: String){
        repository.getEditNotes(id, title, desc)
    }
}