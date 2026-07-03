package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)
    @Delete
    suspend fun deleteQuestion(question: Question)
    @Query("SELECT * FROM Voprosi")
    fun getAllQuestions(): Flow<List<Question>>


}