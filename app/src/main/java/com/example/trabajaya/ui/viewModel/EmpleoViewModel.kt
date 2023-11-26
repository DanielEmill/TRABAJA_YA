package com.example.trabajaya.ui.viewModel


import android.net.Uri
import android.os.Build
import android.util.Log
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
    val selectedUri: Uri? = null
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class EmpleoViewModel @Inject constructor(
    private val empleoRepository: EmpleoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EmpleoListState())
    val uiState: StateFlow<EmpleoListState> = _uiState.asStateFlow()

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
}
