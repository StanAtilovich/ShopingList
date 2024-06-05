package ru.stan.shopinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.stan.shopinglist.R
import ru.stan.shopinglist.activityes.MainApp
import ru.stan.shopinglist.activityes.NewNoteActivity
import ru.stan.shopinglist.databinding.FragmentNoteBinding
import ru.stan.shopinglist.databinding.FragmentShopListNameBinding
import ru.stan.shopinglist.db.MainViewModel
import ru.stan.shopinglist.db.NoteAdapter
import ru.stan.shopinglist.dialogs.NewListDialog
import ru.stan.shopinglist.entities.NoteItem


class ShopListNameFragment : BaseFragment() {
    private lateinit var binding: FragmentShopListNameBinding


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).dataBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {
            override fun onClick(name: String) {
                Log.d("MyLog", "ShoppingList:$name ")
            }

        })
    }

    private fun initRcView() = with(binding) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun observer() {
        mainViewModel.allNotes.observe(viewLifecycleOwner) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopListNameFragment()
    }
}