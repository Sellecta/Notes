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
    fun getAllNotes():LiveData<List<NotesDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: NotesDb)

    @Query("DELETE FROM notes_table WHERE id=:notesId")
    fun deleteNotes(notesId: Int)

    @Query("SELECT * FROM notes_table WHERE id=:notesId LIMIT 1")
     fun getNotesItem(notesId: Int): NotesDb
}
