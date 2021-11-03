package com.example.mynotes

import android.content.Context
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class NoteVMAdapter(private val context:Context, private val listener:ItemClicked): RecyclerView.Adapter<NoteViewHolder>() {

    private val allNote=ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        val viewHolder = NoteViewHolder(view)
        view.setOnClickListener {
            listener.onItemClick(allNote[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNote[position]
        holder.title.text = currentNote.title
        holder.des.text = currentNote.des
    }

    override fun getItemCount(): Int {
        return allNote.size
    }

    fun updateList(newList:List<Note>){
        allNote.clear()
        allNote.addAll(newList)

        notifyDataSetChanged()
    }

}

class NoteViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    val title:TextView = itemView.findViewById(R.id.titleText)
    val des:TextView = itemView.findViewById(R.id.desText)
}

interface ItemClicked{
    fun onItemClick(note:Note)
}