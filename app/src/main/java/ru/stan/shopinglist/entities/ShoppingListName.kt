package ru.stan.shopinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shoppingListNames")
data class ShoppingListName(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "allItemCount")
    val allItemCounter: Int,
    @ColumnInfo(name = "checkItemCounter")
    val checkItemCounter: Int,
    @ColumnInfo(name = "ItemsIds")
    val itemIds: String,

    ) : Serializable
