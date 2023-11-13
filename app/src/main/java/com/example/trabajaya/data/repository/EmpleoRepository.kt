package com.example.trabajaya.data.repository

import com.example.trabajaya.data.remote.dto.EmpleoApi
import com.example.trabajaya.data.remote.dto.EmpleoDto
import com.example.trabajaya.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EmpleoRepository @Inject constructor(
    private val api: EmpleoApi
) {
    fun getEmpleos(): Flow<Resource<List<EmpleoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val empleos = api.getEmpleos()
            emit(Resource.Success(empleos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "Verificar tu conexión a internet"))
        }
    }
    fun getEmpleoId(id: Int): Flow<Resource<EmpleoDto>> = flow {
        try {
            emit(Resource.Loading())
            val empleo = api.getEmpleoId(id)
            emit(Resource.Success(empleo))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "Verificar tu conexión a internet"))
        }
    }

    suspend fun putEmpleo(id: Int, empleoDto: EmpleoDto): Resource<Unit> {
        return try {
            val response = api.putEmpleo(id, empleoDto)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Error PUT Empleo")
            }
        } catch (e: HttpException) {
            Resource.Error(e.message ?: "Error HTTP GENERAL")
        } catch (e: IOException) {
            Resource.Error(e.message ?: "Verificar tu conexión a internet")
        }
    }

    suspend fun deleteEmpleo(id: Int): Resource<Unit> {
        return try {
            val response = api.deleteEmpleo(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Error DELETE Empleo")
            }
        } catch (e: HttpException) {
            Resource.Error(e.message ?: "Error HTTP GENERAL")
        } catch (e: IOException) {
            Resource.Error(e.message ?: "Verificar tu conexión a internet")
        }
    }

    suspend fun postEmpleo(empleoDto: EmpleoDto): Resource<EmpleoDto> {
        return try {
            val response = api.postEmpleo(empleoDto)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Error POST Empleo")
            }
        } catch (e: HttpException) {
            Resource.Error(e.message ?: "Error HTTP GENERAL")
        } catch (e: IOException) {
            Resource.Error(e.message ?: "Verificar tu conexión a internet")
        }
    }

}