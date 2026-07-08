package com.example.myapplication.data

import androidx.room.Embedded
import androidx.room.Relation

data class TestWithQuestions(
    @Embedded val test: Test,
    @Relation(
        parentColumn = "id",
        entityColumn = "testId"
    )
    val questions: List<Question>
)