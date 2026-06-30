package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.callbackFlow
import androidx.compose.foundation.layout.Spacer


class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Glavnaya()
        }
    }
}

data class Question(
    val name:String){
}

@Composable
fun Glavnaya(){
    val questions = remember {
        mutableStateListOf(
            Question("Вопрос 1"),

        )
    }
    Box(modifier = Modifier.fillMaxSize().background(color = Color.LightGray)){
        LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
            items(questions.size){ index ->
                val question = questions[index]
                ListItem(
                    name = question.name
                )
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter).
        fillMaxWidth().
        padding(10.dp)) {
            Adding {
                questions.add(
                    Question(
                        name = "Bопрос ${questions.size + 1}"
                    )
                )
            }
        }
    }
}



@Composable
fun Adding(onClick: () -> Unit){
    Button(modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,       // Цвет фона кнопки
            contentColor = Color.White,       // Цвет текста и иконок
            disabledContainerColor = Color.Gray, // Цвет фона, когда кнопка неактивна
            disabledContentColor = Color.Black   // Цвет текста, когда кнопка неактивна
        )
    ) {Text(text = "+Добавить вопрос",
        style = TextStyle(color = Color.White, fontSize = 38.sp)
    ) }
}

@Composable
private fun ListItem(name: String){
    var task by remember {mutableStateOf("")  }
    var typik by remember {mutableStateOf("Один верный ответ") }
    var expnd by remember { mutableStateOf(false) }
    var state by remember { mutableStateOf(ToggleableState.Indeterminate) }
    Card(modifier = Modifier.fillMaxWidth().
        padding(20.dp).
        shadow(5.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            TextField(
                value = task,
                onValueChange = { task = it },
                label = { Text(name) },
                colors = TextFieldDefaults.colors(

                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,

                    // Цвет фона (заливки) поля
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.LightGray,

                    // Цвет нижней линии (индикатора)
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Gray,

                    // Цвет курсора
                    cursorColor = Color.Red,

                    // Цвет подписи (лейбла)
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Gray,),
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = typik,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(Modifier.weight(1f))

                TriStateCheckbox(
                    state = state,
                    onClick = {
                        // Переключаем между On и Off при клике
                        state = if (state == ToggleableState.On)
                        {ToggleableState.Indeterminate}
                        else if (state == ToggleableState.Indeterminate)
                        {ToggleableState.Off}
                        else ToggleableState.On

                        typik = if (state == ToggleableState.On) "Один верный ответ"
                        else if (state == ToggleableState.Indeterminate)
                        "Несколько верных ответов"
                        else "Ответ необходимо написать"
                    }
                )
                TextField(
                    value = task,
                    onValueChange = { task = it },
                    label = { Text("введите верный ответ") },
                    colors = TextFieldDefaults.colors(

                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray,

                        // Цвет фона (заливки) поля
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.LightGray,

                        // Цвет нижней линии (индикатора)
                        focusedIndicatorColor = Color.Blue,
                        unfocusedIndicatorColor = Color.Gray,

                        // Цвет курсора
                        cursorColor = Color.Red,

                        // Цвет подписи (лейбла)
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Gray,),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}