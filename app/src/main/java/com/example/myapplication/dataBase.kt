package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dataBase(val context: Context, val factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, "practice", factory,1) {
    //передаём контекст имя фактори - хз что это и версию базы данных
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users(id INT PRIMARY KEY, login TEXT, email TEXT, password TEXT)"
        db!!.execSQL(query)//!! для обработки возможного возврата значения НУЛЛ функцией
    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }
    fun addUser(user: User){
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("password", user.password)

        val db = this.writableDatabase
        db.insert("users", null, values)
        db.close()
    }
}