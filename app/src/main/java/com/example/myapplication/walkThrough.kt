package com.example.myapplication

import androidx.compose.foundation.lazy.LazyColumn
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.QuestionType
import androidx.compose.foundation.lazy.items
import java.io.Serializable

class walkThrough : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val testId = intent.getIntExtra("TEST_ID", -1)
        val testName = intent.getStringExtra("TEST_NAME")
        setContent {
            prohodim(testId)
        }
    }
}

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

@Composable
fun prohodim(
    testId: Int,
    viewModel: MainView = viewModel(factory = MainView.factory)
) {
    val context = LocalContext.current
    var testName by remember { mutableStateOf("Загрузка...") }
    var questions by remember { mutableStateOf<List<infaDlyaOtvetov>>(emptyList()) }
    var isLoaded by remember { mutableStateOf(false) }

    // Загружаем данные при открытии экрана
    LaunchedEffect(testId) {
        viewModel.TestTaking(testId) { name, loadedQuestions ->
            testName = name
            questions = loadedQuestions
            isLoaded = true
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        Column(modifier = Modifier.fillMaxSize()) {
            // Заголовок теста
            Text(
                text = testName,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(16.dp)
            )

            // Список вопросов (похож на создание, но для ответов)
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 80.dp) // Отступ для кнопки внизу
            ) {
                items(questions, key = { it.questionId }) { question ->
                    TakingQuestionItem(
                        question = question,
                        onUpdate = { updatedQuestion ->
                            // Находим индекс и обновляем состояние
                            val index = questions.indexOfFirst { it.questionId == updatedQuestion.questionId }
                            if (index != -1) {
                                questions = questions.toMutableList().apply {
                                    this[index] = updatedQuestion
                                }
                            }
                        }
                    )
                }
            }
        }

        // Кнопка завершения внизу экрана
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.checkAndFinishTest(testId, questions) { totalQuestions, correctCount, evaluatedAnswers ->
                        val intent = Intent(context, ResultTest::class.java)
                        intent.putExtra("TEST_NAME", testName)
                        intent.putExtra("TOTAL_QUESTIONS", totalQuestions)
                        intent.putExtra("CORRECT_ANSWERS", correctCount)
                        intent.putExtra("USER_ANSWERS", ArrayList(evaluatedAnswers))

                        context.startActivity(intent)
                        (context as? Activity)?.finish() // Закрываем экран прохождения
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Завершить тест", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}


// Элемент одного вопроса для прохождения (визуально как при создании)
@Composable
fun TakingQuestionItem(
    question: infaDlyaOtvetov,
    onUpdate: (infaDlyaOtvetov) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(15.dp, 10.dp, 15.dp, 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // Текст вопроса (ТОЛЬКО ДЛЯ ЧТЕНИЯ, не CustomTextField)
            Text(
                text = question.task,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            when (question.type) {
                QuestionType.TEXT_INPUT -> {
                    CustomTextField(
                        value = question.textAnswer,
                        onValueChange = {
                            onUpdate(question.copy(textAnswer = it))
                        },
                        label = "Ваш ответ"
                    )
                }
                QuestionType.SINGLE_CHOICE -> {
                    question.options.forEachIndexed { index, optionText ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = question.selectedSingleIndex == index,
                                onClick = {
                                    onUpdate(question.copy(selectedSingleIndex = index))
                                }
                            )
                            Text(text = optionText, modifier = Modifier.padding(start = 8.dp), color = Color.White)
                        }
                    }
                }
                QuestionType.MULTIPLE_CHOICE -> {
                    question.options.forEachIndexed { index, optionText ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = question.checkedIndices.contains(index),
                                onCheckedChange = { isChecked ->
                                    val newChecked = if (isChecked) {
                                        question.checkedIndices + index
                                    } else {
                                        question.checkedIndices - index
                                    }

                                    onUpdate(question.copy(checkedIndices = newChecked))
                                }
                            )
                            Text(text = optionText, modifier = Modifier.padding(start = 8.dp), color = Color.White)
                        }
                    }
                }
            }
        }
    }
}