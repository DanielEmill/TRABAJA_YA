package com.example.trabajaya.utils.navegation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import android.Manifest.permission.CALL_PHONE
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Todo: Direño del menu. Este contiene las direcciones establecidas en el ScreenDirectionModule que serviran como entrada a la pantalla/s deseada/s a ingresar.

// Todo: Nota: Para tener mas control en relacion a la movilidad de las opciones en el contorno del 'ModalNavigationDrawer', se agrego un 'NavigationDrawerItem' y 'items'.

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DrawerMenu(navController: NavController, empleoViewModel: EmpleoViewModel = viewModel()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val item_1 = listOf(
        ScreenDirectionModule.PMain
    )
    val item_2 = listOf(
        ScreenDirectionModule.P2
    )
    val item_3 = listOf(
        ScreenDirectionModule.P1
    )
    val item_4 = listOf(
        ScreenDirectionModule.P3
    )
    val item_5 = listOf(
        ScreenDirectionModule.Pconfig
    )

    val selectedItem_1 = remember { mutableStateOf(item_1[0]) }
    val selectedItem_2 = remember { mutableStateOf(item_2[0]) }
    val selectedItem_3 = remember { mutableStateOf(item_3[0]) }
    val selectedItem_4 = remember { mutableStateOf(item_4[0]) }
    val selectedItem_5 = remember { mutableStateOf(item_5[0]) }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            Spacer(Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomIcon(resourceId = R.drawable.logo)
            }
            Divider(
                modifier = Modifier.fillMaxWidth(), color = Color.Gray
            )
            Spacer(Modifier.height(15.dp))
            item_1.forEach { item ->

                NavigationDrawerItem(
                    icon = {
                        if (item.icon is Int) {
                            Icon(painterResource(id = item.icon), contentDescription = null)
                        } else {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        }
                    },
                    label = { Text(item.title, fontSize = 16.sp, color = Color.Black) },
                    selected = item == selectedItem_1.value,
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem_1.value = item
                        navController.navigate(item.route)
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            item_2.forEach { item ->
                NavigationDrawerItem(
                    icon = {
                        if (item.icon is Int) {
                            Icon(painterResource(id = item.icon), contentDescription = null)
                        } else {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        }
                    },
                    label = { Text(item.title, fontSize = 16.sp, color = Color.Black) },
                    selected = item == selectedItem_2.value,
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem_2.value = item
                        navController.navigate(item.route)
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            item_3.forEach { item ->
                NavigationDrawerItem(
                    icon = {
                        if (item.icon is Int) {
                            Icon(painterResource(id = item.icon), contentDescription = null)
                        } else {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        }
                    },
                    label = { Text(item.title, fontSize = 16.sp, color = Color.Black) },
                    selected = item == selectedItem_3.value,
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem_3.value = item
                        navController.navigate(item.route)
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            item_4.forEach { item ->
                NavigationDrawerItem(
                    icon = {
                        if (item.icon is Int) {
                            Icon(painterResource(id = item.icon), contentDescription = null)
                        } else {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        }
                    },
                    label = { Text(item.title, fontSize = 16.sp, color = Color.Black) },
                    selected = item == selectedItem_4.value,
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem_4.value = item
                        navController.navigate(item.route)
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            item_5.forEach { item ->
                NavigationDrawerItem(
                    icon = {
                        if (item.icon is Int) {
                            Icon(painterResource(id = item.icon), contentDescription = null)
                        } else {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        }
                    },
                    label = { Text(item.title, fontSize = 16.sp, color = Color.Black) },
                    selected = item == selectedItem_5.value,
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem_5.value = item
                        navController.navigate(item.route)
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }, content = {
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
                    imageVector = Icons.Filled.List,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .clickable {
                            scope.launch { drawerState.open() }
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Empleos recientes",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                Image(
                    painter = painterResource(id = R.drawable.workspaces),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
            PantallaInicial(navController, empleoViewModel)
        }
    })
}

@Composable
fun CustomIcon(resourceId: Int) {
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        colorFilter = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(300.dp, 300.dp)
    )
}

// Pantalla principal
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaInicial(navController: NavController, empleoViewModel: EmpleoViewModel = viewModel()) {

    var isModalVisible by remember { mutableStateOf(false) }
    var selectedEmpleo by remember { mutableStateOf<EmpleoDto?>(null) }

    val uiState by empleoViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
                    EmpleoDetails(empleoList = uiState.empleos) {
                        isModalVisible = true
                        selectedEmpleo = it
                    }

                    if (isModalVisible) {
                        MinimalDialog(onDismissRequest = {
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
                            })
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmpleoDetails(
    empleoList: List<EmpleoDto>,
    onEmpleoClick: (EmpleoDto) -> Unit,
) {
    var filtroCategoria by remember { mutableStateOf("") }
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
                        value = filtroCategoria,
                        onValueChange = { filtroCategoria = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(30.dp),
                        placeholder = {
                            Text("Buscar por categoría")
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

        items(empleoList.filter { empleo ->
            empleo.categoria.lowercase().contains(filtroCategoria.lowercase())
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
@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
    empleo: EmpleoDto,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Detalles del empleo",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    IconButton(
                        onClick = { isFavorito = !isFavorito }
                    ) {
                        if (isFavorito) {
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = "Favorito",
                                modifier = Modifier.size(48.dp)
                            )
                            if (currentFavorite == null) {
                                empleoViewModel.GuardarEmpleoFavorito(empleo)
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
                        .padding(16.dp)
                        .height(1.dp),
                    color = Color.DarkGray
                )

                val detalles = listOf(
                    "Nombre" to empleo.nombre,
                    "Categoria" to empleo.categoria,
                    "Descripción" to empleo.descripcion,
                    "Provincia" to empleo.provincia,
                    "Direccion" to empleo.direccion,
                    "Correo" to empleo.correo,
                    "Telefono" to empleo.numero,
                    "Publicado" to fechaFormateada
                )

                detalles.forEach { (titulo, contenido) ->
                    DetalleRow(titulo, contenido)
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(1.dp),
                    color = Color.DarkGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onEnviarCVClick(context, empleo.correo) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Icon(Icons.Default.MailOutline, contentDescription = "Enviar CV")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enviar CV")
                    }

                    Button(
                        onClick = { onContactarClick(context, empleo.numero) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Contactar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Contactar")
                    }
                }
            }
        }
    }
}

@Composable
private fun DetalleRow(titulo: String, contenido: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = contenido, style = MaterialTheme.typography.bodyMedium)
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
            context, CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        context.startActivity(intent)
    } else {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(CALL_PHONE), MY_PERMISSIONS_REQUEST_CALL_PHONE
        )
    }
}