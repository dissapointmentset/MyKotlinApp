package com.example.myapplication

import android.content.Intent
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
        val linkToReg: Button = findViewById(R.id.enter_regbutt)
        val newLog = findViewById<EditText>(R.id.enter_login)
        val newPass = findViewById<EditText>(R.id.enter_psswrd)
        val enterButt: Button = findViewById(R.id.enterbutt)

        linkToReg.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        enterButt.setOnClickListener {
            val login = newLog.text.toString().trim()
            val password = newPass.text.toString().trim()

            if (login != "")
                if (password.length < 8)
                    Toast.makeText(
                        this,
                        "пароль должен содержать не менее восьми символов",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    val db = dataBase(this, null)
                    val vhod = db.getUser(login, password)
                    if (vhod){Toast.makeText(this, "гол", Toast.LENGTH_SHORT).show()
                        val enterIntent = Intent(this, BaseActivity::class.java)
                        startActivity(enterIntent) }
                    else{Toast.makeText(this, "Ноу ноу ноу", Toast.LENGTH_SHORT).show()}
                }
            else Toast.makeText(this, "заполните каждое поле входа", Toast.LENGTH_SHORT)
                .show()
        }
    }
}