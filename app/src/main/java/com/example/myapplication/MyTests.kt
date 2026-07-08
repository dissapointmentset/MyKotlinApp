package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.lifecycle.viewmodel.compose.viewModel

class MyTests : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            spisokTestov()
        }
    }
}


//@Composable
//fun spisokTestov(){
//    Box(modifier = Modifier.fillMaxSize().background(color = Color.White)){
//        LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
//            items(.size){ index ->
//
//            }
//        }
//    }
//}


//@Composable
//fun spisokTestov(
//    viewModel: MainView = viewModel(factory = MainView.factory),
//    onTestClick: (Int) -> Unit = {}
//) {
//    val tests by viewModel.allTestsInfo.collectAsState()
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Сохранённые тесты",
//                style = TextStyle(
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                ),
//                modifier = Modifier.padding(bottom = 24.dp)
//            )
//
//            if (tests.isEmpty()) {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "У вас пока нет тестов",
//                        style = TextStyle(
//                            fontSize = 18.sp,
//                            color = Color.Gray
//                        )
//                    )
//                }
//            } else {
//                LazyColumn(
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(tests, key = { it.id }) { test ->
//                        TestCard(
//                            test = test,
//                            onClick = { onTestClick(test.id) }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TestCard(test: TestInfo, onClick: () -> Unit) {
//    Card(
//        onClick = onClick,
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = test.name,
//                    style = TextStyle(
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black
//                    )
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "${test.questionCount} ${pluralizeQuestions(test.questionCount)}",
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        color = Color.Gray
//                    )
//                )
//            }
//
//            Text(
//                text = "→",
//                fontSize = 24.sp,
//                color = Color.Black
//            )
//        }
//    }
//}
//
//fun pluralizeQuestions(count: Int): String {
//    val lastTwo = count % 100
//    val lastOne = count % 10
//    return when {
//        lastTwo in 11..14 -> "вопросов"
//        lastOne == 1 -> "вопрос"
//        lastOne in 2..4 -> "вопроса"
//        else -> "вопросов"
//    }
//}