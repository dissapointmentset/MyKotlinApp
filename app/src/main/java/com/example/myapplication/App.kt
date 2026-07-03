package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.BasaDanih

class App : Application(){
    val database by lazy { BasaDanih.createDataBase(this) }
}