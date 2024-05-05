package ru.stan.shopinglist.fragments

import androidx.appcompat.app.AppCompatActivity
import ru.stan.shopinglist.R

object FragmentManager {
    private var currentFragment: BaseFragment? = null

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFragment = newFrag
    }
}