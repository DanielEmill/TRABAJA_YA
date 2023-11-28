package com.example.trabajaya.ui.SplashScreen

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trabajaya.R
import androidx.navigation.NavHostController
import com.example.trabajaya.utils.directionModule.ScreenDirectionModule
import kotlinx.coroutines.delay

// Todo: Diseño de la pantalla que se ejecuta al inicio de la aplicacion.

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainSplashScreen(navController: NavHostController): NavHostController {

    LaunchedEffect(key1 = true)
    {
        delay(2000)
        navController.popBackStack()
        navController.navigate(ScreenDirectionModule.PMain.route)
    }
    Splash()
    return navController
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomIcon(resourceId = R.drawable.logo)
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}

@Composable
fun CustomIcon(resourceId: Int) {

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val transition = rememberInfiniteTransition()
    val angles by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Surface (
        modifier = Modifier
            .size(300.dp, 300.dp)
            .rotate(angles)
            .background(Color.White,  RoundedCornerShape(35.dp)),
        shape = RoundedCornerShape(50.dp),
    ) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(320.dp, 300.dp)
                .background(Color.Transparent)
                .rotate(angle)
        )
    }
}