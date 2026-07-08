package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateQuis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            createTest()
        }
    }
}

@Composable
fun createTest(){
    var imya by remember{ mutableStateOf("") }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column( verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                .fillMaxWidth(0.7f)
                .offset(y = -50.dp )
            ) {
            CustomTextField(
                value = imya,
                onValueChange = { imya  = it },
                label = "Введите название теста"
            )
            SubmitButton( onClick = {
                val intent = Intent(context, MainActivity2::class.java)
                intent.putExtra("TEST_NAME", imya)
                context.startActivity(intent)
            },
                enabled = (imya!="")
            )
        }
    }
}

@Composable
fun SubmitButton(onClick: () -> Unit, enabled: Boolean = true) {
    Button(modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,       // Цвет фона кнопки
            contentColor = Color.White,       // Цвет текста и иконок
        )
    ) {Text(text = "Создать тест",
        style = TextStyle(color = Color.White, fontSize = 22.sp)
    ) }
}