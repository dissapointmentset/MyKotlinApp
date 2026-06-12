package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var helwrld = findViewById<TextView>(R.id.StartName)//var - слово для создания переменной
        var ger: EditText =
            findViewById(R.id.gerich)//тип перемнной указывается либо в фигурных скобках либо через двоеточие
        var butt: Button = findViewById(R.id.startbutton)
        butt.setOnClickListener {
            var text = ger.text.toString()
            if (text == "penis")//если ввелось слово пенис, выводим сообщение с помощью библиотеки тост
                Toast.makeText(this, "Ооооо даааа", Toast.LENGTH_SHORT).show()
            else//иначе меняем тест кнопки
                helwrld.text = text
        }
    }
}