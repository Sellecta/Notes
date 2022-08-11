package shadowteam.ua.notes.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import shadowteam.ua.notes.data.database.model.NotesDb

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes():LiveData<List<NotesDb>>

    @Query("SELECT * FROM notes_table")
    suspend fun getAllNotesList():List<NotesDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: NotesDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotesList(notes: List<NotesDb>)

    @Query("DELETE FROM notes_table WHERE id=:notesId")
    suspend fun deleteNotes(notesId: Int)

    @Query("SELECT * FROM notes_table WHERE id=:notesId LIMIT 1")
    suspend fun getNotesItem(notesId: Int): NotesDb
}
