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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ListItem("Янки", Color.Black, 0.5f)
            ListItem("Саньки", Color.Red,0.4f)
            ListItem("Кирила", Color.DarkGray,0.8f)
            ListItem("Алёны", Color.Blue, 1f)

            }

        }
    }
}


@Composable
private fun ListItem(name: String,cvet:Color, dlin: Float){
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
           Column (modifier = Modifier.fillMaxSize().background(color = cvet).padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
               Text(text = "penis\n${name}", style = TextStyle(color = Color.Cyan, fontSize = 22.sp)) }

    }
}
