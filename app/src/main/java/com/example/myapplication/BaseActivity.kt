package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cabinet_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val linkToReg: Button = findViewById(R.id.create_butt)
        linkToReg.setOnClickListener {
            val intent = Intent(this, CreateQuis::class.java)
            startActivity(intent)
        }
        val watchingTests: Button = findViewById(R.id.checklist_butt)
        watchingTests.setOnClickListener {
            val intent = Intent(this, MyTests::class.java)
            startActivity(intent)
        }
    }
}