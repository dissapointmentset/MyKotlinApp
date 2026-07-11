package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "Voprosi",
    foreignKeys = [ForeignKey(
        entity = Test::class,
        parentColumns = ["id"],
        childColumns = ["testId"],
        onDelete = ForeignKey.CASCADE  // При удалении теста удаляются все вопросы
    )],
    indices = [Index("testId")]
)
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val testId: Int,
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