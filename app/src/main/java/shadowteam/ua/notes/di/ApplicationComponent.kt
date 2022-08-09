package shadowteam.ua.notes.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import shadowteam.ua.notes.di.annotation.AppScope
import shadowteam.ua.notes.di.module.DataModule
import shadowteam.ua.notes.presentation.application.NotesApplication

@AppScope
@Component(modules = [DataModule::class])
interface ApplicationComponent {


    fun inject(notesApplication: NotesApplication)

    @Component.Factory
    interface ApplicationComponentFactory{

        fun create(
            @BindsInstance application: Application
        ):ApplicationComponent
    }
}