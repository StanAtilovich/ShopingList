package ru.stan.shopinglist.activityes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.stan.shopinglist.R
import ru.stan.shopinglist.databinding.ActivityMainBinding
import ru.stan.shopinglist.fragments.FragmentManager
import ru.stan.shopinglist.fragments.NoteFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    Log.d("MyLog", "shop_list ")
                }

                R.id.new_item -> {
                    Log.d("MyLog", "new_item ")
                }
            }
            true
        }
    }
}