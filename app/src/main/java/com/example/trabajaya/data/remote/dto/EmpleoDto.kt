package com.example.trabajaya.data.remote.dto

import androidx.room.PrimaryKey

data class EmpleoDto(
    @PrimaryKey var empleoId: Int? = null,
    var nombre: String ="",
    var descripcion: String ="",
    var categoria: String ="",
    var provincia: String ="",
    var fechaDePublicacion: String = "",
    var numero: String = "",
    var correo: String = "",
    var direccion: String = "",
)