package ru.stan.shopinglist.activityes

import android.app.Application
import ru.stan.shopinglist.db.MainDataBase

class MainApp: Application() {
    val dataBase by lazy { MainDataBase.getDataBase(this) }
}