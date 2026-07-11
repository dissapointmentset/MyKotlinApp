package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val testId = intent.getIntExtra("TEST_ID", -1)
        val testName = intent.getStringExtra("TEST_NAME") ?: "Редактирование"

        setContent {
            EditTestScreen(testId = testId, initialTestName = testName)
        }
    }
}

@Composable
fun EditTestScreen(
    testId: Int,
    initialTestName: String,
    viewModel: MainView = viewModel(factory = MainView.factory)
) {
    val context = LocalContext.current
    val questions = remember { mutableStateListOf<QuestionState>() }
    var testName by remember { mutableStateOf(initialTestName) }
    var isLoaded by remember { mutableStateOf(false) }

    //Загружаем вопросы из БД при первом запуске
    LaunchedEffect(testId) {
        viewModel.loadTestForEditing(testId) { name, loadedQuestions ->
            testName = name
            questions.clear()
            questions.addAll(loadedQuestions)
            isLoaded = true
        }
    }

    // Функция проверки готовности (аналогична isTestReady из Glavnaya)
    fun isTestReady(): Boolean {
        if (questions.isEmpty()) return false
        return questions.all { question ->
            when (question.state) {
                ToggleableState.Off -> question.locval.isNotBlank()
                ToggleableState.On -> question.selectedOptionIndex != null &&
                        question.options.all { it.text.isNotBlank() }
                ToggleableState.Indeterminate -> question.options.any { it.isChecked } &&
                        question.options.all { it.text.isNotBlank() }
                else -> false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        if (!isLoaded) {
            // Показываем индикатор загрузки
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
                items(questions.size) { index ->
                    ListItem(
                        questionState = questions[index],
                        onUpdate = { updatedQuestion ->
                            questions[index] = updatedQuestion
                        },
                        onDelete = {
                            if (questions.size > 1) {
                                questions.removeAt(index)
                            }
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Save(
                    onClick = {
                        // ✅ ОБНОВЛЕНИЕ вместо создания
                        viewModel.updateTest(
                            testId = testId,
                            testName = testName,
                            questions = questions.toList()
                        ) {
                            (context as? Activity)?.finish()
                        }
                    },
                    enabled = questions.isNotEmpty() && isTestReady()
                )

                Spacer(modifier = Modifier.weight(1f))

                Adding {
                    questions.add(QuestionState())
                }
            }
        }
    }
}