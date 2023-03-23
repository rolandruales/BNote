package com.example.mynotesapp.data

import androidx.annotation.NonNull
import androidx.room.*

//database entities
@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull @ColumnInfo(name = "note_title")
    val noteTitle: String,

    @NonNull @ColumnInfo(name = "note_description")
    val noteDescription: String,

    @NonNull @ColumnInfo(name = "note_date")
    val noteDate: String

)

