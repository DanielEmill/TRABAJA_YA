package com.example.trabajaya.ui.Screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun P3Screen(
    navController: NavController, ) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedUri = it }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { selectedUri = it }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sube tu CV",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Nosotros te buscamos el trabajo",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        UploadButton(onClick = {
            launcher.launch("application/pdf")
        })

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedUri != null) {
            SendButton(onClick = {
                selectedUri?.let { uri ->
                    sendEmailWithAttachment(context, uri)
                }
            })
        }
    }
}

@Composable
fun UploadButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
            .size(56.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Subir CV",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun SendButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
            .size(56.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Enviar",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

private fun sendEmailWithAttachment(context: Context, uri: Uri) {
    val emailIntent = Intent(Intent.ACTION_SEND)
    emailIntent.type = "text/plain"
    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Reclutamiento@TrabajaYa.com"))
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CV Universal")

    val file = File(context.cacheDir, "CV_Universal.pdf")
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    inputStream?.use { input ->
        val outputStream = FileOutputStream(file)
        input.copyTo(outputStream)
        outputStream.close()
    }

    val contentUri = FileProvider.getUriForFile(
        context, context.packageName + ".fileprovider", file
    )

    emailIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    context.startActivity(Intent.createChooser(emailIntent, "Enviar CV"))
}