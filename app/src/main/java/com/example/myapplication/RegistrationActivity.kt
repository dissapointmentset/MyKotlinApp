package com.example.myapplication

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        val newLog = findViewById<EditText>(R.id.login)
        val newEmal = findViewById<EditText>(R.id.email)
        val newPass = findViewById<EditText>(R.id.psswrd)

    }
}