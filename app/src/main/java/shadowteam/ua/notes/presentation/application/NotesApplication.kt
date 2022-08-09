package shadowteam.ua.notes.presentation.application

import android.app.Application
import shadowteam.ua.notes.di.DaggerApplicationComponent

class NotesApplication :Application() {

    val component by lazy{
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}