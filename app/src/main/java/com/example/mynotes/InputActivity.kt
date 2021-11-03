package com.example.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlin.properties.Delegates

class InputActivity : AppCompatActivity() {
    lateinit var delete:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)


        val titleInput:EditText = findViewById(R.id.titleInput)
        val desInput:EditText = findViewById(R.id.desInput)
        val titleText = intent.getStringExtra("title")
        val desText = intent.getStringExtra("des")
        titleInput.setText(titleText)
        desInput.setText(desText)
        delete = if(titleText!=null){
            "yes"
        }else{
            "no"
        }
    }

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
        finish()
    }

    fun save(view: View) {
        saveNote()
        val title: EditText = findViewById(R.id.titleInput)
        val titleText = title.text.toString()
        if (titleText.isNotEmpty()) {
            finish()
        }
    }

    fun delete(view: View) {
        val data = Intent()
        setResult(RESULT_CANCELED, data)
        finish()
    }

    private fun saveNote(){
        val title: EditText = findViewById(R.id.titleInput)
        val des: EditText = findViewById(R.id.desInput)
        val titleText = title.text.toString()
        val desText = des.text.toString()
        if(titleText.isNotEmpty()){
            val data = Intent()
            data.putExtra("title", titleText)
            data.putExtra("des", desText)

            data.putExtra("toDelete", delete)
            setResult(RESULT_OK, data)
        }
    }


}