package com.example.trabajaya.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.trabajaya.data.local.entities.EmpleoLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpleoDao {
    @Upsert
    suspend fun save(empleo: EmpleoLocal)
    @Query(
        """
            SELECT *
            FROM Empleos
            WHERE empleoId=:id
            LIMIT 1
        """
    )
    suspend fun find(id:Int) : EmpleoLocal?
    @Delete
    suspend fun delete(empleo: EmpleoLocal)
    @Query("SELECT * FROM Empleos")
    fun getAll() : Flow<List<EmpleoLocal>>
}