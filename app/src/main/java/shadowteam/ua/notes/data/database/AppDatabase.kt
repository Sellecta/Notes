package shadowteam.ua.notes.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import shadowteam.ua.notes.data.database.dao.NotesDao
import shadowteam.ua.notes.data.database.model.NotesDb

@Database(entities = [NotesDb::class], version = 2, exportSchema = false)
abstract class AppDatabase :RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "notes_db"
        private var db:AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(application: Application):AppDatabase{
            synchronized(LOCK){
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                db = instance
                return instance
            }
        }
    }

    abstract fun notesDao(): NotesDao
}