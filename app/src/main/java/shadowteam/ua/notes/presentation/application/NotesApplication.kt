package shadowteam.ua.notes.presentation.application

import android.app.Application
import androidx.work.Configuration
import shadowteam.ua.notes.data.worker.RefreshDataWorkerFactory
import shadowteam.ua.notes.di.DaggerApplicationComponent
import javax.inject.Inject

class NotesApplication :Application(), Configuration.Provider {

    @Inject
    lateinit var refreshDataWorkerFactory: RefreshDataWorkerFactory


    val component by lazy{
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(refreshDataWorkerFactory)
            .build()
    }
}