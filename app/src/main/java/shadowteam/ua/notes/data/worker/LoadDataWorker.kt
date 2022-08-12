package shadowteam.ua.notes.data.worker

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.*
import kotlinx.coroutines.delay
import shadowteam.ua.notes.data.database.dao.NotesDao
import shadowteam.ua.notes.data.mapper.NotesMapper
import shadowteam.ua.notes.data.network.ApiService
import shadowteam.ua.notes.domain.dataclass.Notes
import javax.inject.Inject

class LoadDataWorker(
    appContext: Context,
    private val params: WorkerParameters,
    private val notesDao: NotesDao,
    private val mapper: NotesMapper,
    private val apiService: ApiService
) :CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try{
            params.inputData.getString(DELAY)?.let {delayTime ->
                var countNoteDb = notesDao.getAllNotesList().size
                val dbEmptyBoolean = workDataOf(DB_EMPTY_KEY to (countNoteDb > 0))
                setProgress(dbEmptyBoolean)
                val loadData = loadDataImitation(delayTime)
                if(loadData.isNotEmpty()){ notesDao.insertNotesList(loadData.map {
                    mapper.mapNotesToNotesDb(it) })}
                countNoteDb = notesDao.getAllNotesList().size
                Result.success(workDataOf(LOAD_DATA_KEY to countNoteDb))
            }
            Result.success()
        }catch (e:Exception){
            val countNoteDb = notesDao.getAllNotesList().size
            setProgress(workDataOf(NOT_INTERNET_KEY  to true, INTERNET_PROBLEM_STYLE_KEY to countNoteDb))
            while (checkInternet() == 0){
                delay(10000)
            }
            Result.retry()
        }
    }

    class Factory @Inject constructor(
        private val notesDao: NotesDao,
        private val mapper: NotesMapper,
        private val apiService: ApiService
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return LoadDataWorker(appContext, params, notesDao, mapper, apiService)
        }
    }

    private suspend fun loadDataImitation(delay: String):List<Notes>{ //method Imitation load data retrofit...
        val listNotesLoad = mutableListOf<Notes>()
        apiService.getNotesDelay(delay)
        listNotesLoad.add(Notes(1, "EXAMPLE", "DESCRIPTION EXAMPLE", (10000000000..System.currentTimeMillis()).random()))
        listNotesLoad.add(Notes(2, "EXAMPLE_2", "DESCRIPTION EXAMPLE_2", (10000000000..System.currentTimeMillis()).random()))
        listNotesLoad.add(Notes(3, "EXAMPLE_1", "DESCRIPTION EXAMPLE_3 LOOOOONNNNGGGGGGGG MASSAGE", (10000000000..System.currentTimeMillis()).random()))
        return listNotesLoad
    }



    private fun checkInternet():Int{
                var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        result = 3
                    }
                }
        return result
    }

    companion object{
        const val NAME_WORKER = "RefreshDataWorker"
        const val DB_EMPTY_KEY = "db_empty"
        const val NOT_INTERNET_KEY = "not_internet"
        const val INTERNET_PROBLEM_STYLE_KEY = "internet_problem_style"
        const val LOAD_DATA_KEY = "load_data_end"
        private const val DELAY = "delay"
        fun makeRequest(delay: String):OneTimeWorkRequest{
            return OneTimeWorkRequestBuilder<LoadDataWorker>()
                .setInputData(workDataOf(DELAY to delay))
                .build()
        }
    }
}