package com.example.trabajaya.data.repository

import com.example.trabajaya.data.remote.EmpleoApi
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
}