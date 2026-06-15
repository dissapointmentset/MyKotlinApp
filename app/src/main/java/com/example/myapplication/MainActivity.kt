package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val helwrld =
            findViewById<TextView>(R.id.StartName)//val, var - слово для создания переменной
        val logField: EditText =
            findViewById(R.id.gerich)//тип перемнной указывается либо в фигурных скобках либо через двоеточие
        val paswrdFld: EditText = findViewById(R.id.enterPsswrd)
        val butstart: Button = findViewById(R.id.startbutton)

//        butstart.setOnClickListener {
//            val login = logField.text.toString().trim()
//            val passwrd = paswrdFld.text.toString().trim()
//            if (login != "some52@mail.ru")//если ввелось слово пенис, выводим сообщение с помощью библиотеки тост
//                Toast.makeText(this, "Неверный емаил", Toast.LENGTH_SHORT).show()
//            else if (passwrd == "dupashmupa") helwrld.text = login
//            else Toast.makeText(this, "Неверный пароль", Toast.LENGTH_SHORT).show()
//        }
    }
}