package com.example.trabajaya.ui.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabajaya.utils.directionModule.ScreenDirectionModule
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun P2Screen(navController: NavController) {
    val scope =  rememberCoroutineScope()
    Column(
        modifier = Modifier
            .size(200.dp, 200.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp, 50.dp)
                .padding(4.dp)
                .clickable {
                    scope.launch { navController.navigate(ScreenDirectionModule.PMain.route) }
                }
        )
        Spacer(modifier = Modifier.padding(200.dp))
        Text(text = "Pantalla #2", fontSize = 40.sp, modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
            .wrapContentWidth(
                Alignment.CenterHorizontally
            ))
    }
}