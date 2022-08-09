package shadowteam.ua.notes.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import shadowteam.ua.notes.data.database.model.NotesDb

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table")
    fun getAllNotes():LiveData<NotesDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: NotesDb)
}
