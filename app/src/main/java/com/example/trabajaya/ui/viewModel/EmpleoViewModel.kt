package com.example.trabajaya.ui.viewModel


import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabajaya.data.local.EmpleoRepositoryLocal
import com.example.trabajaya.data.local.entities.EmpleoLocal
import com.example.trabajaya.data.remote.dto.EmpleoDto
import com.example.trabajaya.data.repository.EmpleoRepository
import com.example.trabajaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
data class EmpleoListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val empleos: List<EmpleoDto> = emptyList(),
    val selectedUri: Uri? = null
)


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class EmpleoViewModel @Inject constructor(
    private val empleoRepository: EmpleoRepository,
    private val empleoRepositoryLocal: EmpleoRepositoryLocal
) : ViewModel() {
    private val _uiState = MutableStateFlow(EmpleoListState())
    val uiState: StateFlow<EmpleoListState> = _uiState.asStateFlow()

    var favorites : StateFlow<List<EmpleoLocal>> = empleoRepositoryLocal.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
    init {
        Log.d("EmpleoViewModel", "ViewModel initialized")
        loadEmpleos()
    }
    fun setSelectedUri(uri: Uri) {
        _uiState.update { it.copy(selectedUri = uri) }
    }
    fun loadEmpleos() {
        viewModelScope.launch {
            try {
                Log.d("EmpleoViewModel", "Loading empleos...")
                empleoRepository.getEmpleos().collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                            Log.d("EmpleoViewModel", "Loading empleos - Loading...")
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    empleos = result.data ?: emptyList(),
                                    isLoading = false,
                                    error = null
                                )
                            }
                            Log.d("EmpleoViewModel", "Loading empleos - Success")
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message ?: "Error desconocido",
                                    isLoading = false
                                )
                            }
                            Log.e("EmpleoViewModel", "Loading empleos - Error: ${result.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("EmpleoViewModel", "Loading empleos - Exception: ${e.message}")
            }
        }

    }
    fun GuardarEmpleoFavorito(empleo: EmpleoDto){
        viewModelScope.launch {
            val Empleo = EmpleoLocal(
                nombre = empleo.nombre,
                descripcion = empleo.descripcion,
                categoria = empleo.categoria,
                provincia = empleo.provincia,
                fechaDePublicacion = empleo.fechaDePublicacion,
                numero = empleo.numero,
                correo = empleo.correo,
                direccion = empleo.direccion
            )
            empleoRepositoryLocal.save(Empleo)
        }
    }

    fun Borradordefavorito(empleo: EmpleoLocal){
        viewModelScope.launch {
            empleoRepositoryLocal.delete(empleo)
        }
    }

}
