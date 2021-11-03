package com.example.mynotes

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ItemClicked {
    lateinit var toDeleteNote: Note
    private lateinit var viewModel: NoteViewModel
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tempNote = Note("!", "1")
        toDeleteNote = tempNote
        var spanCount = 0

        val orientation = Configuration()
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 2
        } else if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        val adapter = NoteVMAdapter(this, this)
        recyclerView.adapter = adapter


        viewModel = ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer { list ->

            list?.let {
                adapter.updateList(it)
            }

        })
        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode== RESULT_OK){
                val titleText = it.data?.getStringExtra("title").toString()
                val desText = it.data?.getStringExtra("des").toString()
                val toDelete = it.data?.getStringExtra("toDelete").toString()
                if(titleText.isNotEmpty()){
                    viewModel.insert(Note(titleText, desText))
                    if (toDelete=="yes"){
                        viewModel.delete(toDeleteNote)
                    }
                }
            }else if(it.resultCode== RESULT_CANCELED) viewModel.delete(toDeleteNote)
        }
    }

    fun addNote(view: View) {
        val intent = Intent(this, InputActivity::class.java)
        activityResultLauncher.launch(intent)
    }

    override fun onItemClick(note: Note) {
        val intent = Intent(this, InputActivity::class.java)
        val noteTitle = note.title
        val noteDes = note.des
        intent.putExtra("title", noteTitle)
        intent.putExtra("des", noteDes)
        toDeleteNote = note
        activityResultLauncher.launch(intent)
    }
}
