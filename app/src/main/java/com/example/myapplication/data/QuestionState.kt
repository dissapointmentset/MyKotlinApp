package com.example.myapplication.data

import androidx.compose.ui.state.ToggleableState

data class QuestionState(
    val task: String = "",
    val state: ToggleableState = ToggleableState.Indeterminate,
    val options: List<AnswerOption> = listOf(AnswerOption(), AnswerOption()),
    val selectedOptionIndex: Int? = null,
    val locval: String = ""
)