package com.example.trabajaya.utils.navegation

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabajaya.R
import com.example.trabajaya.utils.directionModule.ScreenDirectionModule
import kotlinx.coroutines.launch

// Todo: Direño del menu. Este contiene las direcciones establecidas en el ScreenDirectionModule que serviran como entrada a la pantalla/s deseada/s a ingresar.

// Todo: Nota: Para tener mas control en relacion a la movilidad de las opciones en el contorno del 'ModalNavigationDrawer', se agrego un 'NavigationDrawerItem' y 'items'.

@Composable
fun DrawerMenu(
    navController: NavController
) {
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CustomIcon(resourceId = R.drawable.painter_icon)
                }
                Spacer(Modifier.height(15.dp))
                item_1.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            if (item.icon is Int) {
                                Icon(painterResource(id = item.icon), contentDescription = null)
                            } else {
                                // Manejar el caso en el que item.icon no es de tipo Int
                                // Puedes proporcionar un icono predeterminado o manejarlo según tus necesidades
                                Icon(Icons.Default.Warning, contentDescription = null)
                            }
                        },
                        label = { Text(item.title, fontSize = 25.sp) },
                        selected = item == selectedItem_1.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem_1.value = item
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                item_2.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            if (item.icon is Int) {
                                Icon(painterResource(id = item.icon), contentDescription = null)
                            } else {
                                // Manejar el caso en el que item.icon no es de tipo Int
                                // Puedes proporcionar un icono predeterminado o manejarlo según tus necesidades
                                Icon(Icons.Default.Warning, contentDescription = null)
                            }
                        },
                        label = { Text(item.title, fontSize = 25.sp) },
                        selected = item == selectedItem_2.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem_2.value = item
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                item_3.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            if (item.icon is Int) {
                                Icon(painterResource(id = item.icon), contentDescription = null)
                            } else {
                                // Manejar el caso en el que item.icon no es de tipo Int
                                // Puedes proporcionar un icono predeterminado o manejarlo según tus necesidades
                                Icon(Icons.Default.Warning, contentDescription = null)
                            }
                        },
                        label = { Text(item.title, fontSize = 25.sp) },
                        selected = item == selectedItem_3.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem_3.value = item
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                item_4.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            if (item.icon is Int) {
                                Icon(painterResource(id = item.icon), contentDescription = null)
                            } else {
                                // Manejar el caso en el que item.icon no es de tipo Int
                                // Puedes proporcionar un icono predeterminado o manejarlo según tus necesidades
                                Icon(Icons.Default.Warning, contentDescription = null)
                            }
                        },
                        label = { Text(item.title, fontSize = 25.sp) },
                        selected = item == selectedItem_4.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem_4.value = item
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.padding(200.dp))
                item_5.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            if (item.icon is Int) {
                                Icon(painterResource(id = item.icon), contentDescription = null)
                            } else {
                                // Manejar el caso en el que item.icon no es de tipo Int
                                // Puedes proporcionar un icono predeterminado o manejarlo según tus necesidades
                                Icon(Icons.Default.Warning, contentDescription = null)
                            }
                        },
                        label = { Text(item.title, fontSize = 25.sp) },
                        selected = item == selectedItem_5.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem_5.value = item
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .padding(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp, 50.dp)
                        .padding(4.dp)
                        .clickable {
                            scope.launch { drawerState.open() }
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
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = null,
                    tint = Color.Cyan,
                    modifier = Modifier
                        .size(180.dp, 180.dp)
                        .padding(4.dp),
                )
            }
        }
    )
}

@Composable
fun CustomIcon(resourceId: Int) {
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        colorFilter = null,
        contentScale = ContentScale.Fit, // Ajusta según tus necesidades
        modifier = Modifier
            .size(300.dp, 300.dp)
    )
}
