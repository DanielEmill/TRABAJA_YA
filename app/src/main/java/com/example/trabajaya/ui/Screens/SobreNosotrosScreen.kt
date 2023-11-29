package com.example.trabajaya.ui.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabajaya.R
import com.example.trabajaya.utils.directionModule.ScreenDirectionModule
import kotlinx.coroutines.launch
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConfigScreen(navController: NavController) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        IconButton(
            onClick = { scope.launch { navController.navigate(ScreenDirectionModule.PMain.route) } },
            modifier = Modifier
                .size(50.dp)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sobre Nosotros - TRABAJAYA",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Proyecto realizado por Emill Daniel Garcia Castellanos y Wandy Fco. Paulino Victorino.",
            fontSize = 16.sp,
            modifier = Modifier
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(16.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "TrabajaYA es un prototipo diseñado para abordar la problemática del desempleo y facilitar la búsqueda de empleo.",
            fontSize = 16.sp,
            modifier = Modifier
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(16.dp)
                .padding(bottom = 8.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
        )
    }
}
