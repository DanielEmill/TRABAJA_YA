package com.example.trabajaya.utils.directionModule

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material.icons.twotone.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.trabajaya.R


// Todo: Inicializando las direcciones para utilizarlas en el DrawerMenu.

sealed class ScreenDirectionModule (val route: String, val title: String, val icon: Int) {

    object PMain : ScreenDirectionModule("p_main", "Empleos nuevos", R.drawable.file_copy)

    object P1: ScreenDirectionModule("p1", "Busqueda avanzada",  R.drawable.people)
    object P2 : ScreenDirectionModule("p2", "Vacante favorito", R.drawable.star)
    object P3 : ScreenDirectionModule("p3", "Enviar CV", R.drawable.access_time)

    object Pconfig : ScreenDirectionModule("p_config", "Sobre Nosotros", R.drawable.people)

    object Splash : ScreenDirectionModule("splash","", R.drawable.add_home_work)

}