package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.TestInfo

class MyTests : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            spisokTestov()
        }
    }
}

@Composable
fun TestCard(testInfo: TestInfo, onClick: () -> Unit,
             viewModel: MainView = viewModel(factory = MainView.factory)
) {

    var expnd by remember { mutableStateOf(true) }
    val context = LocalContext.current
    var openDialog by remember { mutableStateOf(false) }

    Card(
        onClick = {expnd=!expnd},
        modifier = Modifier.fillMaxWidth().padding(15.dp,10.dp,15.dp,8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Обращаемся к name через @Embedded
                Text(
                    text = testInfo.test.name,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Используем questionCount
                Text(
                    text = "Вопросов - ${(testInfo.questionCount)}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                )
                AnimatedVisibility(
                    visible = expnd,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Row() {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                val intent = Intent(context, EditActivity::class.java)
                                intent.putExtra("TEST_ID", testInfo.test.id)
                                intent.putExtra("TEST_NAME", testInfo.test.name)
                                context.startActivity(intent)},
                            modifier = Modifier
                                .fillMaxWidth(0.33f)
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            )
                        ) {
                            Text(text = "изменить", color = Color.White)
                        }

                        Button(
                            onClick = {val intent = Intent(context, walkThrough::class.java)
                                intent.putExtra("TEST_ID", testInfo.test.id)
                                intent.putExtra("TEST_NAME", testInfo.test.name)
                                context.startActivity(intent)},
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            )
                        ) {
                            Text("Пройти", color = Color.White)
                        }
                        Button(
                            onClick = {openDialog = true},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            )
                        ) {
                            Text("Удалить", color = Color.White)
                        }
                        if (openDialog){
                            AlertDialog(
                                onDismissRequest = { openDialog = false },
                                // configure confirm button
                                confirmButton = {
                                    Button(onClick = { openDialog = false
                                        viewModel.deleteTest(testInfo.test.id)
                                    }) {
                                        // set button text
                                        Text("ДА!!!!")
                                    }
                                },
                                // configure dismiss button
                                dismissButton = {
                                    TextButton(onClick = { openDialog = false }) {
                                        // set button text
                                        Text("Назад")
                                    }
                                },
                                icon = {
                                    Icon(imageVector = Icons.Default.Warning , contentDescription = "Warning Icon" )
                                },
                                title = {
                                    Text(text = "ЭЭЭЭЭЭЭЭ", color = Color.Black)
                                },
                                text = {
                                    Text(text = "Вы точно хотите удалить тест?", color = Color.DarkGray)
                                },
                                modifier = Modifier.padding(16.dp),
                                shape = RoundedCornerShape(16.dp),
                                containerColor = Color.White,
                                iconContentColor = Color.Red,
                                titleContentColor = Color.Black,
                                textContentColor = Color.DarkGray,
                                tonalElevation = 8.dp,
                                properties = DialogProperties(
                                    dismissOnBackPress = true,
                                    dismissOnClickOutside = false
                                )
                            ) // Кнопка отмены
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun spisokTestov(
    viewModel: MainView = viewModel(factory = MainView.factory),
    onTestClick: (Int) -> Unit = {}){
    val tests by viewModel.allTestsInfo.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(color = Color.White)){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tests, key = { it.test.id }) { testInfo ->
                TestCard(
                    testInfo = testInfo,
                    onClick = { onTestClick(testInfo.test.id)})
            }
        }
    }
}
