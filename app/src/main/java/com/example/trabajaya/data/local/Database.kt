package com.example.trabajaya.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trabajaya.data.local.dao.EmpleoDao
import com.example.trabajaya.data.local.entities.EmpleoLocal

@Database(
    entities = [EmpleoLocal::class], version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun EmpleoDao(): EmpleoDao
}