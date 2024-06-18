package ru.stan.shopinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.stan.shopinglist.entities.NoteItem
import ru.stan.shopinglist.entities.ShoppingListItem
import ru.stan.shopinglist.entities.ShoppingListName

@Dao
interface Dao {
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM shoppingListNames")
    fun getAllShopListNames(): Flow<List<ShoppingListName>>

    @Insert
    suspend fun insertNote(noteItem: NoteItem)

    @Insert
    suspend fun insertShopListName(name: ShoppingListName)
    @Query("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)
    @Update
    suspend fun updateNote(note: NoteItem)
}