package com.example.myapplication.data

data class AnswerOption(
    val id: Int = System.currentTimeMillis().toInt(),
    var text: String = "",
    var isChecked: Boolean = false
)