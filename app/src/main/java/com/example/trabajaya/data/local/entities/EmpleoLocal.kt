package com.example.trabajaya.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Empleos")
data class EmpleoLocal(
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
