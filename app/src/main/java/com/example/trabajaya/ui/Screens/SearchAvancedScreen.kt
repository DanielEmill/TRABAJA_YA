package com.example.trabajaya.ui.Screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.trabajaya.utils.navegation.*
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
            PantallaBA(navController, empleoViewModel)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaBA(navController: NavController, empleoViewModel: EmpleoViewModel = viewModel()) {

    var isModalVisible by remember { mutableStateOf(false) }
    var selectedEmpleo by remember { mutableStateOf<EmpleoDto?>(null) }
    var filtroBusqueda by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val uiState by empleoViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .clickable {
                        scope.launch { navController.navigate(ScreenDirectionModule.PMain.route ) }
                    },
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Busqueda avanzada",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp),
                tint = MaterialTheme.colorScheme.background
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
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
                    EmpleoDetailsF(
                        empleoList = uiState.empleos,
                        filtroCategoria = filtroBusqueda,
                        onEmpleoClick = {
                            isModalVisible = true
                            selectedEmpleo = it
                        }
                    )

                    if (isModalVisible) {
                        MinimalDialog(
                            onDismissRequest = {
                                isModalVisible = false
                                selectedEmpleo = null
                            },
                            empleo = selectedEmpleo ?: EmpleoDto(),
                            onEnviarCVClick = { context, correo ->
                                onEnviarCVClick(
                                    context,
                                    correo
                                )
                            },
                            onContactarClick = { context, numero ->
                                onContactarClick(
                                    context,
                                    numero
                                )
                            }
                        )
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
    filtroCategoria: String,
    onEmpleoClick: (EmpleoDto) -> Unit,
) {
    var filtroBusqueda by remember { mutableStateOf("") }
    var isFilterVisible by remember { mutableStateOf(false) }

    LazyColumn {
        item {
            if (isFilterVisible) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = filtroBusqueda,
                        onValueChange = { filtroBusqueda = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(30.dp),
                        placeholder = {
                            Text("Buscar por nombre, categoria, provincia, etc...")
                        },
                        singleLine = true,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isFilterVisible = !isFilterVisible
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                )
            }
        }

        items( empleoList.filter { empleo ->
            empleo.provincia.lowercase().contains(filtroBusqueda.lowercase()) ||
                    empleo.categoria.lowercase().contains(filtroBusqueda.lowercase()) ||
                    empleo.direccion.lowercase().contains(filtroBusqueda.lowercase()) ||
                    empleo.descripcion.lowercase().contains(filtroBusqueda.lowercase())||
                    empleo.nombre.lowercase().contains(filtroBusqueda.lowercase()) ||
                    empleo.fechaDePublicacion.lowercase().contains(filtroBusqueda.lowercase())
        }) { empleo ->
            val fechaParseada =
                LocalDateTime.parse(empleo.fechaDePublicacion, DateTimeFormatter.ISO_DATE_TIME)
            val fechaFormateada = fechaParseada.format(DateTimeFormatter.ISO_DATE)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        onEmpleoClick(empleo)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${empleo.nombre}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.click_png_45032),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
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
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "${empleo.descripcion}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Publicado el: $fechaFormateada",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}