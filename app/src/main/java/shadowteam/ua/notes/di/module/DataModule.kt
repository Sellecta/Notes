package shadowteam.ua.notes.di.module

import dagger.Binds
import dagger.Module
import shadowteam.ua.notes.data.repository.NotesRepositoryImpl
import shadowteam.ua.notes.di.annotation.AppScope
import shadowteam.ua.notes.domain.domaininterface.NotesRepository

@Module
interface DataModule {

    @Binds
    @AppScope
    fun bindRepositoryNotes(impl:NotesRepositoryImpl): NotesRepository
}