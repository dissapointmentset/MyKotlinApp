package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Voprosi")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val vopros: String,
    val questionType: QuestionType,
    val varianti: List<String>,
    val otvet: String,
    val indexiOtveta: List<Int>
)
enum class QuestionType {
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    TEXT_INPUT
}