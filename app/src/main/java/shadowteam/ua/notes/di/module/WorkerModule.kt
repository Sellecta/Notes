package shadowteam.ua.notes.di.module

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import shadowteam.ua.notes.data.worker.ChildWorkerFactory
import shadowteam.ua.notes.data.worker.LoadDataWorker

import shadowteam.ua.notes.di.annotation.WorkerKey

@Module
interface WorkerModule {

    @IntoMap
    @WorkerKey(LoadDataWorker::class)
    @Binds
    fun bindRefreshDataWorkerFactory(impl: LoadDataWorker.Factory): ChildWorkerFactory

}