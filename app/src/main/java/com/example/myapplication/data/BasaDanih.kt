package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Question::class
    ],
    version = 1
)
abstract class BasaDanih: RoomDatabase() {
    abstract val dao: MyDao
    companion object{
        fun createDataBase(context: Context): BasaDanih{
            return Room.databaseBuilder(
                context, BasaDanih::class.java,
                "testDB"
            ).build()
        }
    }

}