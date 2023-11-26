package com.example.trabajaya.data.local

import com.example.trabajaya.data.local.dao.EmpleoDao
import com.example.trabajaya.data.local.entities.EmpleoLocal
import javax.inject.Inject

class EmpleoRepositoryLocal @Inject constructor(private val empleoDao: EmpleoDao) {
    suspend fun save(empleo: EmpleoLocal) = empleoDao.save(empleo)
    suspend fun delete(empleo: EmpleoLocal) = empleoDao.delete(empleo)
    suspend fun find(id: Int) = empleoDao.find(id)
    fun getAll() = empleoDao.getAll()
}