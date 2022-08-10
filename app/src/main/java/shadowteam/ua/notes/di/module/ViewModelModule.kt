package shadowteam.ua.notes.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import shadowteam.ua.notes.di.annotation.ViewModelKey
import shadowteam.ua.notes.presentation.viewmodel.EditNotesViewModel
import shadowteam.ua.notes.presentation.viewmodel.NotesViewModel
import shadowteam.ua.notes.presentation.viewmodel.ViewModelFactory

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    @Binds
    fun bindsNotesViewModel(impl: NotesViewModel):ViewModel

    @IntoMap
    @ViewModelKey(EditNotesViewModel::class)
    @Binds
    fun bindsEditNotesViewModel(impl:EditNotesViewModel):ViewModel
}