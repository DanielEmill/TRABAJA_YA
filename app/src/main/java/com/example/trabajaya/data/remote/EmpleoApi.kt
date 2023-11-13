package com.example.trabajaya.data.remote.dto

import retrofit2.Response
import retrofit2.http.*

interface EmpleoApi {

    @GET("api/Empleos")
    suspend fun getEmpleos(): List<EmpleoDto>

    @GET("api/Empleos/{id}")
    suspend fun getEmpleoId(@Path("id") id: Int): EmpleoDto

    @POST("api/Empleos")
    suspend fun postEmpleo(@Body empleoDto: EmpleoDto): Response<EmpleoDto>

    @PUT("api/Empleos/{id}")
    suspend fun putEmpleo(@Path("id") id: Int, @Body empleoDto: EmpleoDto): Response<Unit>

    @DELETE("api/Empleos/{id}")
    suspend fun deleteEmpleo(@Path("id") id: Int): Response<Unit>
}