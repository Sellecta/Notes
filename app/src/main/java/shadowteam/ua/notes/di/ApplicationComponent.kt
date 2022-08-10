package shadowteam.ua.notes.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import shadowteam.ua.notes.di.annotation.AppScope
import shadowteam.ua.notes.di.module.DataModule
import shadowteam.ua.notes.di.module.ViewModelModule
import shadowteam.ua.notes.di.module.WorkerModule
import shadowteam.ua.notes.presentation.application.NotesApplication
import shadowteam.ua.notes.presentation.ui.EditNotesFragment
import shadowteam.ua.notes.presentation.ui.NotesFragment

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class, WorkerModule::class])
interface ApplicationComponent {


    fun inject(notesApplication: NotesApplication)
    fun inject(notesApplication: NotesFragment)
    fun inject(editNotesFragment: EditNotesFragment)

    @Component.Factory
    interface ApplicationComponentFactory{

        fun create(
            @BindsInstance application: Application
        ):ApplicationComponent
    }
}