package shadowteam.ua.notes.di.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import shadowteam.ua.notes.data.database.AppDatabase
import shadowteam.ua.notes.data.database.dao.NotesDao
import shadowteam.ua.notes.data.repository.NotesRepositoryImpl
import shadowteam.ua.notes.di.annotation.AppScope
import shadowteam.ua.notes.domain.domaininterface.NotesRepository

@Module
interface DataModule {

    @Binds
    @AppScope
    fun bindRepositoryNotes(impl: NotesRepositoryImpl): NotesRepository

    companion object {

        @Provides
        @AppScope
        fun provideCoinDao(application: Application): NotesDao {
            return AppDatabase.getInstance(application).notesDao()
        }
    }
}