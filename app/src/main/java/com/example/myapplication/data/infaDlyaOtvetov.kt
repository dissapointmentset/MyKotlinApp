package com.example.myapplication.data

import java.io.Serializable

data class infaDlyaOtvetov(
    val questionId: Int,
    val questionNumber: Int,
    val task: String,
    val type: QuestionType,
    val options: List<String>,
    val selectedSingleIndex: Int? = null,      // Для одного правильного ответа
    val checkedIndices: List<Int> = emptyList(), // Для нескольких
    val textAnswer: String = "",                 // Для текстового ответа
    val isCorrect: Boolean = false
): Serializable