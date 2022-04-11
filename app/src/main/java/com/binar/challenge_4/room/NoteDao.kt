package com.binar.challenge_4.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.binar.challenge_4.data.NoteList

@Dao
interface NoteDao {
    @Query("SELECT * FROM NoteList")
    fun getAllNotes(): List<NoteList>

    @Insert(onConflict = REPLACE)
    fun insertNotes(noteList: NoteList):Long

    @Update
    fun updateNotes(noteList: NoteList?):Int

    @Delete
    fun deleteNotes(noteList: NoteList):Int
}