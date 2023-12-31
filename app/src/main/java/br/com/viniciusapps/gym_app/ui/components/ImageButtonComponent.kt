package br.com.viniciusapps.gym_app.ui.components


import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PickImageButton(): Uri? {
    var imageUri = remember { mutableStateOf<Uri?>(null) }
    var launcher = ImagePickAction(imageUri)

    IconButton(
        modifier = Modifier
            .wrapContentSize(align = Center)
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        onClick = { launcher.launch("image/*") }) {
        Icon(imageVector = Icons.Filled.Photo, contentDescription = "Selecionar Imagem")
    }

    return imageUri.value
}

@Composable
fun ImagePickAction(imageUri: MutableState<Uri?>): ManagedActivityResultLauncher<String, Uri?> {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri.value = uri
        }
    )
    return launcher
}



