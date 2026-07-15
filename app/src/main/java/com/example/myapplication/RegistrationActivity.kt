package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.User


class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        val newLog = findViewById<EditText>(R.id.login)
        val newEmal = findViewById<EditText>(R.id.email)
        val newPass = findViewById<EditText>(R.id.psswrd)
        val butt: Button = findViewById(R.id.regbutt)

        butt.setOnClickListener {
            val login = newLog.text.toString().trim()
            val email = newEmal.text.toString().trim()
            val password = newPass.text.toString().trim()

            if (login != "" || email != "")
                if (password.length < 8)
                    Toast.makeText(
                        this,
                        "пароль должен содержать не менее восьми символов",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    val user = User(login, email, password)

                    val db = dataBase(this, null)
                    db.addUser(user)
                    Toast.makeText(this, "Зарегался", Toast.LENGTH_SHORT).show()

                    newLog.text.clear()
                    newEmal.text.clear()
                    newPass.text.clear()

                }
            else Toast.makeText(this, "заполните каждое поле регистрации", Toast.LENGTH_SHORT)
                .show()


        }

    }
}