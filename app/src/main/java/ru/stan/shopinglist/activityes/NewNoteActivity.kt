package ru.stan.shopinglist.activityes

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.annotation.SuppressLint
import android.content.ContextParams
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.getSpans
import ru.stan.shopinglist.R
import ru.stan.shopinglist.databinding.ActivityNewNoteBinding
import ru.stan.shopinglist.entities.NoteItem
import ru.stan.shopinglist.fragments.NoteFragment
import ru.stan.shopinglist.utils.HtmlManager
import ru.stan.shopinglist.utils.MyTouchListener
import ru.stan.shopinglist.utils.TimeManager
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
        init()
        onClickColorPicker()
        actionMenuCallback()
    }

    private fun setColorForSelectedText(colorId: Int) = with(binding) {
        val startPos = edDiscription.selectionStart
        val endPos = edDiscription.selectionEnd
        val styles = edDiscription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)
        if (styles.isNotEmpty()) edDiscription.text.removeSpan(styles[0])

        edDiscription.text.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this@NewNoteActivity, colorId)),
            startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        edDiscription.text.trim()
        edDiscription.setSelection(startPos)
    }

    private fun onClickColorPicker() = with(binding) {
        imBlack.setOnClickListener { setColorForSelectedText(R.color.picker_black) }
        imGreen.setOnClickListener { setColorForSelectedText(R.color.picker_green) }
        imYellow.setOnClickListener { setColorForSelectedText(R.color.picker_yellow) }
        imOrange.setOnClickListener { setColorForSelectedText(R.color.picker_orange) }
        imBlue.setOnClickListener { setColorForSelectedText(R.color.picker_blue) }
        imRed.setOnClickListener { setColorForSelectedText(R.color.picker_red) }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
    }

    private fun updateNote(): NoteItem? = with(binding) {
        return note?.copy(
            title = edTitle.text.toString(),
            content = HtmlManager.toHtml(edDiscription.text)
        )
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getNote() {
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null)
            note = sNote as NoteItem
        fillNote()
    }

    private fun fillNote() = with(binding) {
        edTitle.setText(note?.title)
        note?.content?.let { edDiscription.setText(HtmlManager.getFromHtml(it).trim()) }
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
        ) {
            setBoldForSelectedText()
        } else if (item.itemId == R.id.color) {
            if (binding.colorPicker.isShown) {
                closeColorPicker()
            } else {
                openColorPicker()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldForSelectedText() = with(binding) {
        val startPos = edDiscription.selectionStart
        val endPos = edDiscription.selectionEnd
        val styles = edDiscription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()) {
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
            if (note == null) {
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



    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.toHtml(binding.edDiscription.text),
           TimeManager.getCurrentTime(),
            ""
        )
    }

    private fun actionBarSettings() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker() {
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        openAnim.setAnimationListener(object : AnimatorListener, Animation.AnimationListener {
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {

            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {

            }

            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        binding.colorPicker.startAnimation(openAnim)
    }
    private fun actionMenuCallback(){
        val actionCallback = object :ActionMode.Callback{
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear()
                return true
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear()
                return true
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {

            }

        }
        binding.edDiscription.customSelectionActionModeCallback = actionCallback
    }
}