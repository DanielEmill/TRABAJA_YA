package com.example.trabajaya.ui.Screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun P1Screen(navController: NavController, empleoViewModel: EmpleoViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .size(200.dp, 200.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(20.dp))
            PantallaBA(navController, empleoViewModel)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaBA(navController: NavController, empleoViewModel: EmpleoViewModel = viewModel()) {

    val transition = rememberInfiniteTransition()
    val angles by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val scope = rememberCoroutineScope()

    var isModalVisible by remember { mutableStateOf(false) }
    var selectedEmpleo by remember { mutableStateOf<EmpleoDto?>(null) }

    val uiState by empleoViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(30.dp)
                    .clickable {
                        scope.launch { navController.navigate(ScreenDirectionModule.PMain.route) }
                    },
                tint = Color.White
            )
            Text(
                text = "Busca tu provincia",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.click_png_45032),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
                    .rotate(angles),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
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
    onEmpleoClick: (EmpleoDto) -> Unit,
) {
    var filtrar by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

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
                        .padding(16.dp)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                filtrar = ""
                            }
                        },
                    shape = RoundedCornerShape(30.dp),
                    interactionSource = interactionSource
                )
            }
        }

        // Todo: Creacion de un filtro para la busqueda de empleos por provincia.
        // Todo: La variable llamada filteredEmployments es la que se encarga de filtrar los empleos.

        val filtrarEmpleos = empleoList.filter { empleo ->
            val buscar = filtrar.lowercase()
            empleo.provincia.lowercase().contentEquals(buscar) ||
                    empleo.provincia.lowercase().contains(buscar) ||
                    empleo.categoria.lowercase().contains(buscar)
        }

        items(filtrarEmpleos) { empleo ->
            val fechaParseada =
                LocalDateTime.parse(empleo.fechaDePublicacion, DateTimeFormatter.ISO_DATE_TIME)
            val fechaFormateada = fechaParseada.format(DateTimeFormatter.ISO_DATE)
            Surface(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .clickable {
                        onEmpleoClick(empleo)
                    }
                    .shadow(elevation = 15.dp, shape = RoundedCornerShape(16.dp))
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${empleo.nombre}",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Blue,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.click_png_45032),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(10.dp)
                                    .clickable {
                                        onEmpleoClick(empleo)
                                    }
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "${empleo.categoria} - ${empleo.provincia}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray
                            )
                            Text(
                                text = "Publicado el: $fechaFormateada",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}