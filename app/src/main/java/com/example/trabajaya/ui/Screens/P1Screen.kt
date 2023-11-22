

package com.example.trabajaya.ui.Screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trabajaya.R
import com.example.trabajaya.data.remote.dto.EmpleoDto
import com.example.trabajaya.ui.viewModel.EmpleoViewModel
import com.example.trabajaya.utils.directionModule.ScreenDirectionModule
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun P1Screen(navController: NavController, empleoViewModel: EmpleoViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .size(200.dp, 200.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ){
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                        scope.launch { navController.navigate(ScreenDirectionModule.PMain.route) }
                    }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PantallaBA(empleoViewModel)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaBA(empleoViewModel: EmpleoViewModel = viewModel()) {

    var isModalVisible by remember { mutableStateOf(false) }
    var selectedEmpleo by remember { mutableStateOf<EmpleoDto?>(null) }

    val uiState by empleoViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Busqueda Avanzada:",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                ) {
                    LinearProgressIndicator()
                }
            }

            uiState.error != null -> {
                Text("Error: ${uiState.error}")
            }

            uiState.empleos != null -> {
                Column {
                    EmpleoDetailsF(empleoList = uiState.empleos) {
                        isModalVisible = true
                        selectedEmpleo = it
                    }

                    if (isModalVisible) {
                        com.example.trabajaya.utils.navegation.MinimalDialog(onDismissRequest = {
                            isModalVisible = false
                            selectedEmpleo = null
                        },
                            empleo = selectedEmpleo ?: EmpleoDto(),
                            onEnviarCVClick = { context, correo ->
                                com.example.trabajaya.utils.navegation.onEnviarCVClick(
                                    context,
                                    correo
                                )
                            },
                            onContactarClick = { context, numero ->
                                com.example.trabajaya.utils.navegation.onContactarClick(
                                    context,
                                    numero
                                )
                            })
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmpleoDetailsF(
    empleoList: List<EmpleoDto>,
    onEmpleoClick: (EmpleoDto) -> Unit
) {
    var filtrar by remember { mutableStateOf("Ingresar") }

    LazyColumn {
        item {
            // Todo: Casilla de busqueda de empleos por provincia.
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = filtrar,
                    onValueChange = { filtrar = it },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(30.dp)
                )
            }
        }

        // Todo: Creacion de un filtro para la busqueda de empleos por provincia.

        // Todo: La variable llamada filteredEmployments es la que se encarga de filtrar los empleos

        val filtrarEmpleos = empleoList.filter { empleo ->
            val Buscar = filtrar.lowercase()
            empleo.provincia.lowercase().contentEquals(Buscar) ||
                    empleo.provincia.lowercase().contains(Buscar) ||
                    empleo.provincia.lowercase().startsWith(Buscar) ||
                    empleo.provincia.lowercase().endsWith(Buscar)
        }

        val Buscar = filtrar.lowercase()

        val filtrarPorProvincias = if (filtrar.lowercase().contains(Buscar)) {
            filtrarEmpleos
        } else {
            filtrarEmpleos.filter { it.provincia.lowercase() == filtrar.lowercase() }
        }

        items(filtrarPorProvincias) { empleo ->
            val fechaParseada =
                LocalDateTime.parse(empleo.fechaDePublicacion, DateTimeFormatter.ISO_DATE_TIME)
            val fechaFormateada = fechaParseada.format(DateTimeFormatter.ISO_DATE)
            Surface(
                modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "${empleo.nombre} - ${empleo.categoria}",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "${empleo.descripcion}",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Gray.copy(alpha = 0.2f))
                                .padding(vertical = 8.dp)
                        )
                        Text(
                            text = "Publicado el: $fechaFormateada en ${empleo.provincia}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.click_png_45032),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { onEmpleoClick(empleo) }
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp), color = Color.Gray
            )
        }
    }
}