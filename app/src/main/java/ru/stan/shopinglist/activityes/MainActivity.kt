package ru.stan.shopinglist.activityes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.stan.shopinglist.R
import ru.stan.shopinglist.databinding.ActivityMainBinding
import ru.stan.shopinglist.dialogs.NewListDialog
import ru.stan.shopinglist.fragments.FragmentManager
import ru.stan.shopinglist.fragments.NoteFragment
import ru.stan.shopinglist.fragments.ShopListNameFragment

class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentManager.setFragment(ShopListNameFragment.newInstance(), this)
        setBottomNavigationListener()
    }

    private fun setBottomNavigationListener() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.setting -> {
                    Log.d("MyLog", "settings ")
                }

                R.id.notes -> {
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }

                R.id.shop_list -> {
                    FragmentManager.setFragment(ShopListNameFragment.newInstance(), this)
                }

                R.id.new_item -> {
                    FragmentManager.currentFragment?.onClickNew()

                }
            }
            true
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLog", "onClick:$name ")
    }
}