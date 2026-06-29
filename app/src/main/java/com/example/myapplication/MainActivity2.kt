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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.callbackFlow


class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(modifier = Modifier.fillMaxSize().background(color = Color.LightGray)){
                LazyColumn() {
                    items(1){
                        ListItem("Янки", Color.Black, 0.5f, "немытый")
                        ListItem("Саньки", Color.Red,0.4f, "вонючий")
                        ListItem("Кирила", Color.DarkGray,0.8f, "Несокрушимый")
                        ListItem("Алёны", Color.Blue, 1f, "Гигантский")
                    }
                }
                Box(modifier = Modifier.align(Alignment.BottomCenter).
                fillMaxWidth().
                padding(10.dp)) { Adding()}
            }
        }
    }
}

//data class Question(
//    val name:String,
//    val color: Color, val length: Float, val descript: String){
//}
//
//@Composable
//fun Glavnaya(){
//    val questions = remember {
//        mutableStateListOf(
//            Question("Янки", Color.Black, 0.5f, "немытый"),
//            Question("Саньки", Color.Red, 0.4f, "вонючий"),
//            Question("Кирила", Color.DarkGray, 0.8f, "Несокрушимый"),
//            Question("Алёны", Color.Blue, 1f, "Гигантский")
//        )
//    }
//}

@Composable
fun Adding(){
    Button(modifier = Modifier.fillMaxWidth(), onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,       // Цвет фона кнопки
            contentColor = Color.White,       // Цвет текста и иконок
            disabledContainerColor = Color.Gray, // Цвет фона, когда кнопка неактивна
            disabledContentColor = Color.Black   // Цвет текста, когда кнопка неактивна
        )
    ) {Text(text = "+Добавить вопрос", style = TextStyle(color = Color.White, fontSize = 38.sp)) }
}

@Composable
private fun ListItem(name: String,cvet:Color, dlin: Float, desc: String){
    var expnd by remember { mutableStateOf(false) }
    var dln = remember{mutableStateOf(dlin)}
    Card(modifier = Modifier.fillMaxWidth(dln.value).
    padding(20.dp).
    shadow(5.dp).
    clickable{dln.value+=0.01f
        Log.d("MyLog","Пенис ${name} увеличели")}.pointerInput(Unit){
        detectHorizontalDragGestures { change, dragAmount ->
            Log.d("MyLog","Пенис ${name} почухан") }},
        shape = RoundedCornerShape(15.dp)
    ) {
        Column (modifier = Modifier.fillMaxSize()
            .background(color = cvet)
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(modifier = Modifier.clickable {expnd=!expnd},
                maxLines = if(expnd) 2 else 3,
                text = "penis\n${name}\n(${desc})",
                style = TextStyle(color = Color.Cyan, fontSize = 22.sp)) }

    }
}