package ru.stan.shopinglist.activityes

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.getSpans
import ru.stan.shopinglist.R
import ru.stan.shopinglist.databinding.ActivityNewNoteBinding
import ru.stan.shopinglist.entities.NoteItem
import ru.stan.shopinglist.fragments.NoteFragment
import ru.stan.shopinglist.utils.HtmlManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()
    }
    private fun updateNote(): NoteItem? = with(binding){
        return note?.copy(title = edTitle.text.toString(),
            content = HtmlManager.toHtml(edDiscription.text))
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getNote(){
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null)
        note = sNote as NoteItem
        fillNote()
    }
    private fun fillNote() = with(binding){
                    edTitle.setText(note?.title)
            edDiscription.setText(HtmlManager.getFromHtml(note?.content!!).trim())

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            setMainResult()
        } else if (
            item.itemId == android.R.id.home) {
            finish()
        } else if (
            item.itemId == R.id.bold
        ){
            setBoldForSelectedText()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldForSelectedText() = with(binding) {
        val startPos = edDiscription.selectionStart
        val endPos = edDiscription.selectionEnd
        val styles = edDiscription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()){
            edDiscription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }

        edDiscription.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDiscription.text.trim()
        edDiscription.setSelection(startPos)
    }

    private fun setMainResult() {
        var editState = "new"
        val tempNote: NoteItem? =
        if (note == null){
            createNewNote()
        } else {
            editState = "update"
            updateNote()
        }
        val i = Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.toHtml(binding.edDiscription.text),
            getCurrentTime(),
            ""
        )
    }

    private fun actionBarSettings() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}