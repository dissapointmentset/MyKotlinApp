package com.example.myapplication.data

import androidx.room.Embedded

data class TestInfo(
    @Embedded val test: Test,      // ← Room сам возьмет id и name из класса Test
    val questionCount: Int         // ← Единственное уникальное поле
)
