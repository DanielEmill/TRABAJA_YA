package com.example.trabajaya.ui.viewModel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajaya.data.remote.dto.EmpleoDto
import com.example.trabajaya.data.repository.EmpleoRepository
import com.example.trabajaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class EmpleoListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val empleos: List<EmpleoDto> = emptyList(),
    val empleoEditando: EmpleoDto? = null
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class EmpleoViewModel @Inject constructor(
    private val empleoRepository: EmpleoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EmpleoListState())
    val uiState: StateFlow<EmpleoListState> = _uiState.asStateFlow()

    var nombre by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var categoria by mutableStateOf("")
    var fechaDePublicacion by mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    var numero by mutableStateOf("")
    var correo by mutableStateOf("")
    var direccion by mutableStateOf("")

    var isValidNombre by mutableStateOf(true)
    var isValidDescripcion by mutableStateOf(true)
    var isValidCategoria by mutableStateOf(true)
    var isValidFechaDePublicacion by mutableStateOf(true)
    var isValidNumero by mutableStateOf(true)
    var isValidICorreo by mutableStateOf(true)
    var isValidDireccion by mutableStateOf(true)

    private fun isValid(): Boolean {
        isValidNombre = nombre.isNotBlank()
        isValidDescripcion = descripcion.isNotBlank()
        isValidCategoria = categoria.isNotBlank()
        isValidFechaDePublicacion = fechaDePublicacion.isNotBlank()
        isValidNumero = numero.isNotBlank()

        if (!isValidNombre) println("")
        if (!isValidDescripcion) println("")
        if (!isValidCategoria) println("")
        if (!isValidFechaDePublicacion) println("")
        if (!isValidNumero) println("")

        return isValidNombre && isValidDescripcion && isValidFechaDePublicacion
    }
    //


    init {
        loadEmpleos()
    }

    private fun loadEmpleos() {
        viewModelScope.launch {
            empleoRepository.getEmpleos().collect() { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                empleos = result.data ?: emptyList(), isLoading = false, error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.message ?: "Error desconocido", isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun postEmpleo() {
        viewModelScope.launch {
            if (isValid()) {
                println("Guardando gasto...")

                val fechaActual = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
                val nuevoEmpleoDto = EmpleoDto(
                    fechaDePublicacion = fechaActual,
                    nombre = nombre,
                    descripcion = descripcion,
                    categoria = categoria,
                    numero = numero,
                    correo = correo,
                    direccion = direccion
                )
                try {
                    val result = empleoRepository.postEmpleo(nuevoEmpleoDto)
                    if (result is Resource.Success) {
                        val empleoCreado = result.data
                        empleoCreado?.let {
                            val updatedEmpleo = _uiState.value.empleos + it
                            _uiState.update { state -> state.copy(empleos = updatedEmpleo) }
                            limpiar()
                            println("Empleo guardado!")
                            loadEmpleos()
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(
                            error = (result as Resource.Error).message ?: "Error desconocido"
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(error = e.message ?: "Error desconocido")
                }
            } else {
                println("Datos de Empleo no son válidos.")
            }
        }
    }
    fun deleteEmpleo(id: Int) {
        viewModelScope.launch {
            try {
                val result = empleoRepository.deleteEmpleo(id)
                if (result is Resource.Success) {
                    val updatedEmpleo = _uiState.value.empleos.filter { it.empleoId != id }
                    _uiState.update { state -> state.copy(empleos = updatedEmpleo) }
                    println("Empleo eliminado!")
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = (result as Resource.Error).message ?: "Error desconocido"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Error desconocido")
            }
        }
    }

    var selectedEmpleoId by mutableStateOf(0)

    fun editarEmpleo(empleo: EmpleoDto) {
        _uiState.update { state -> state.copy(empleoEditando = empleo) }
        nombre = empleo.nombre ?: ""
        descripcion = empleo.descripcion ?: ""
        categoria = empleo.categoria ?: ""
        fechaDePublicacion = empleo.fechaDePublicacion ?: ""
        numero = empleo.numero ?: ""
        correo = empleo.correo ?: ""
        direccion = empleo.direccion ?: ""

    }



    fun updateEmpleo() {
        viewModelScope.launch {
            if (isValid()) {
                println("Actualizando empleo...")

                val empleoEditando = uiState.value.empleoEditando
                val fechaActual = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))

                val empleoActualizado = EmpleoDto(
                    nombre = nombre,
                    descripcion = descripcion,
                    categoria = categoria,
                    fechaDePublicacion = fechaDePublicacion,
                    numero = numero,
                    correo = correo,
                    direccion = direccion

                )
                try {
                    val result =
                        empleoRepository.putEmpleo(empleoActualizado.empleoId!!, empleoActualizado)
                    if (result is Resource.Success) {
                        val updatedGastos =
                            _uiState.value.empleos.map { if (it.empleoId == empleoActualizado.empleoId) empleoActualizado else it }
                        _uiState.update { state ->
                            state.copy(
                                empleos = updatedGastos, empleoEditando = null
                            )
                        }
                        limpiar()
                        println("Gasto actualizado!")
                        loadEmpleos()
                    } else {
                        _uiState.value = _uiState.value.copy(
                            error = (result as Resource.Error).message ?: "Error desconocido"
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(error = e.message ?: "Error desconocido")
                }
            } else {
                println("Datos de gasto no son válidos.")
            }
        }
    }

    private fun limpiar() {
        nombre = ""
        descripcion = ""
        categoria = ""
        fechaDePublicacion = ""
        numero = ""
        correo = ""
        direccion = ""
    }
}