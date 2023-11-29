package com.example.trabajaya.ui.Screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trabajaya.R
import com.example.trabajaya.data.local.entities.EmpleoLocal
import com.example.trabajaya.data.remote.dto.EmpleoDto
import com.example.trabajaya.ui.viewModel.EmpleoViewModel
import com.example.trabajaya.utils.directionModule.ScreenDirectionModule
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun P2Screen(navController: NavController, empleoViewModel: EmpleoViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.size(200.dp, 200.dp), horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        PantallaInicial(navController, empleoViewModel)
    }
}

// Pantalla principal
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaInicial(navController: NavController, empleoViewModel: EmpleoViewModel = viewModel()) {

    var isModalVisible by remember { mutableStateOf(false) }
    var selectedEmpleo by remember { mutableStateOf<EmpleoLocal?>(null) }
    val favorites by empleoViewModel.favorites.collectAsState()
    val scope = rememberCoroutineScope()

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
                text = "Mis Favoritos",
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

        Column {
            EmpleoDetails(empleoList = favorites) {
                isModalVisible = true
                selectedEmpleo = it
            }

            if (isModalVisible) {
                MinimalDialog(onDismissRequest = {
                    isModalVisible = false
                    selectedEmpleo = null
                }, empleo = selectedEmpleo ?: EmpleoLocal(),

                    onEnviarCVClick = { context, correo ->
                        onEnviarCVClick(context, correo)
                    }, onContactarClick = { context, numero ->
                        onContactarClick(context, numero)
                    })
            }
        }
    }
}

// EmpleoDetails
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmpleoDetails(empleoList: List<EmpleoLocal>, onEmpleoClick: (EmpleoLocal) -> Unit) {
    LazyColumn {
        items(empleoList) { empleo ->
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

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
    empleo: EmpleoLocal,
    onEnviarCVClick: (Context, String) -> Unit,
    onContactarClick: (Context, String) -> Unit,
    empleoViewModel: EmpleoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val fechaParseada =
        LocalDateTime.parse(empleo.fechaDePublicacion, DateTimeFormatter.ISO_DATE_TIME)
    val fechaFormateada = fechaParseada.format(DateTimeFormatter.ISO_DATE)


    val favorites by empleoViewModel.favorites.collectAsState()
    var isFavorito by mutableStateOf(false)
    val currentFavorite = favorites.find { it.empleoId == empleo.empleoId }
    if (currentFavorite != null) {
        isFavorito = true
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.Top)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.Top)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Detalles del empleo",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { isFavorito = !isFavorito },
                    ) {
                        if (isFavorito) {
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = "Favorito",
                                modifier = Modifier.size(48.dp)
                            )
                            if (currentFavorite == null) {

                                var empleoDto: EmpleoDto? = null
                                val uiState by empleoViewModel.uiState.collectAsStateWithLifecycle()
                                empleoDto = uiState.empleos.find { it.empleoId == empleo.empleoId }
                                if (empleoDto != null) {
                                    empleoViewModel.GuardarEmpleoFavorito(empleoDto)
                                }
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Filled.StarBorder,
                                contentDescription = "Favorite",
                                modifier = Modifier.size(48.dp)
                            )
                            if (currentFavorite != null) {
                                empleoViewModel.Borradordefavorito(currentFavorite)
                            }
                        }
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp), color = Color.Gray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Nombre:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("${empleo.nombre}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Categoria:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("${empleo.categoria}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Descripción:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("${empleo.descripcion}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Provincia:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("${empleo.provincia}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Direccion:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("${empleo.direccion}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Correo:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("${empleo.correo}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Telefono:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("${empleo.numero}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Publicado:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("$fechaFormateada")
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp), color = Color.Gray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { onEnviarCVClick(context, empleo.correo) }) {
                        Text("Enviar CV")
                    }
                    Button(onClick = { onContactarClick(context, empleo.numero) }) {
                        Text("Contactar")
                    }
                }
            }
        }
    }
}

fun onEnviarCVClick(context: Context, correo: String) {
    val subject = "CV - BY TRABAJAYA"
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$correo")
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    context.startActivity(intent)
}


private const val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1
fun onContactarClick(context: Context, numero: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:$numero")
    }
    if (ContextCompat.checkSelfPermission(
            context, Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        context.startActivity(intent)
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CALL_PHONE),
            MY_PERMISSIONS_REQUEST_CALL_PHONE
        )
    }
}