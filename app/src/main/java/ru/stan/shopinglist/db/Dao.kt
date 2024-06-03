package ru.stan.shopinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.stan.shopinglist.entities.NoteItem

@Dao
interface Dao {
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Insert
    suspend fun insertNote(noteItem: NoteItem)
    @Query("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)
}