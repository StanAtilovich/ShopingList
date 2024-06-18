package ru.stan.shopinglist.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.stan.shopinglist.entities.NoteItem
import ru.stan.shopinglist.entities.ShoppingListName
import java.lang.IllegalArgumentException

class MainViewModel(dataBase: MainDataBase): ViewModel() {
    val dao = dataBase.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNames: LiveData<List<ShoppingListName>> = dao.getAllShopListNames().asLiveData()

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }
    fun insertShopListName(listName: ShoppingListName) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }
    fun deleteNote(id : Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }
    fun updateNote(note: NoteItem) =  viewModelScope.launch {
        dao.updateNote(note)
    }

    class MainViewModelFactory(val dataBase: MainDataBase): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
               @Suppress("UNCHECKED_CAST")
                return MainViewModel(dataBase) as T
            }
            throw IllegalArgumentException("unknown viewModelClass")
        }
    }
}