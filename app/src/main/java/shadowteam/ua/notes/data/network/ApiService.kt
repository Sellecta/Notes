package shadowteam.ua.notes.data.network

import kotlinx.coroutines.Delay
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("delay/{delay}")
    suspend fun getNotesDelay(@Path(DELAY) delay: String)

    companion object {
        private const val DELAY = "delay"

    }
}