package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateQuis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_quis)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val numberPicker = findViewById<NumberPicker>(R.id.test_kolvo)
        numberPicker.minValue = 0
        numberPicker.maxValue = 100
        numberPicker.value = 1

        val DaleeButt: Button = findViewById(R.id.dalee_one)

        DaleeButt.setOnClickListener {
            val intent = Intent(this, CreateQuis::class.java)
            startActivity(intent)
        }
    }
}