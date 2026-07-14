package com.example.myapplication

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import com.example.myapplication.data.QuestionType

class ResultTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestResultsScreen(
                testName = intent.getStringExtra("TEST_NAME") ?: "Тест",
                totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0),
                correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0),
                userAnswers = emptyList(), // или получите из ViewModel
                onBackClick = { finish() },
                onRetakeClick = { finish() }
            )
        }
    }
}

@Composable
fun TestResultsScreen(
    testName: String,
    totalQuestions: Int,
    correctAnswers: Int,
    userAnswers: List<infaDlyaOtvetov>,
    onBackClick: () -> Unit,
    onRetakeClick: () -> Unit
) {
    val percentage = (correctAnswers.toFloat() / totalQuestions * 100).toInt()

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            // ✅ Карточка с результатом
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (percentage >= 50) Color(0xFF4CAF50) else Color(0xFFF44336)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Большой текст с результатом
                        Text(
                            text = "$correctAnswers/$totalQuestions",
                            style = TextStyle(
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "правильных ответов",
                            style = TextStyle(fontSize = 18.sp, color = Color.White),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = "$percentage%",
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            // Заголовок
            item {
                Text(
                    text = testName,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            // Список вопросов с отметками
            items(userAnswers) { answer ->
                QuestionResultItem(answer = answer)
            }

            // Отступ снизу
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // Кнопки внизу
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = onBackClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Назад", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onRetakeClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Пройти снова", color = Color.White)
            }
        }
    }
}

// Элемент одного вопроса в результатах
@Composable
fun QuestionResultItem(answer: infaDlyaOtvetov) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = "Вопрос #${answer.questionId}",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
            Text(
                text = answer.task,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Показываем ответ пользователя
            when (answer.type) {
                QuestionType.TEXT_INPUT -> {
                    Text(text = "Ваш ответ: ${answer.textAnswer.ifBlank { "Нет ответа" }}", style = TextStyle(fontSize = 14.sp, color = Color.Black))
                }
                QuestionType.SINGLE_CHOICE -> {
                    val userText = answer.selectedSingleIndex?.let { answer.options.getOrNull(it) } ?: "Нет ответа"
                    Text(text = "Ваш ответ: $userText", style = TextStyle(fontSize = 14.sp, color = Color.Black))
                }
                QuestionType.MULTIPLE_CHOICE -> {
                    val count = answer.checkedIndices.size
                    Text(text = "Выбрано вариантов: $count", style = TextStyle(fontSize = 14.sp, color = Color.Black))
                }
            }

            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (answer.isCorrect) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                    contentDescription = null,
                    tint = if (answer.isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = if (answer.isCorrect) "Верно" else "Ошибка",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (answer.isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // 💡оказать правильный ответ, если пользователь ошибся
            if (!answer.isCorrect) {
                // понадобится доступ к правильному ответу из БД.
                // передам его в infaDlyaOtvetov  поле correctAnswerText: String,
                // вы можете вывести его здесь серым цветом для обучения пользователя.
            }
        }
    }
}