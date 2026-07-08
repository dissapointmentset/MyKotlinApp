package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
@Database(
    entities = [
        Test::class,      //список сущностей
        Question::class
    ],
    version = 1,          //Версия 1, БД новая
    exportSchema = false
)
@TypeConverters(Converters::class)  //конвертеры для List
abstract class BasaDanih : RoomDatabase() {

    //абстрактный метод для TestDao
    abstract fun testDao(): TestDao
    abstract fun MyDao(): MyDao
    companion object {
        @Volatile
        private var INSTANCE: BasaDanih? = null

        fun createDataBase(context: Context): BasaDanih {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BasaDanih::class.java,
                    "testDB"
                ).build()
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { INSTANCE = it }
            }
        }
    }
}