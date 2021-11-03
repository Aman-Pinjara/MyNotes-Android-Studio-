package com.example.mynotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(@ColumnInfo(name = "title")val title:String, @ColumnInfo(name = "des")val des:String){
    @PrimaryKey(autoGenerate = true)var id = 0
}