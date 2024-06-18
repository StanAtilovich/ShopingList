package ru.stan.shopinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.stan.shopinglist.entities.LibraryItem
import ru.stan.shopinglist.entities.NoteItem
import ru.stan.shopinglist.entities.ShoppingListItem
import ru.stan.shopinglist.entities.ShoppingListName

@Database(
    entities = [LibraryItem::class, NoteItem::class, ShoppingListItem::class, ShoppingListName::class],
    version = 1
)
abstract class MainDataBase : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "shoping_list.db"
                ).build()
                instance
            }
        }
    }
}