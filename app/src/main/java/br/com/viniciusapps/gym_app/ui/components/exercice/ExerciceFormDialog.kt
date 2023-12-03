package br.com.viniciusapps.gym_app.ui.components.exercice


import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.viniciusapps.gym_app.infra.firebase.storage.Storage
import br.com.viniciusapps.gym_app.infra.utils.uriToByteArray
import br.com.viniciusapps.gym_app.infra.validation.input.validate
import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import br.com.viniciusapps.gym_app.ui.components.LoadingDialog
import br.com.viniciusapps.gym_app.ui.components.PickImageButton
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, Long, Uri, String,Boolean?) -> Unit,
    exercicio: Exercicio
) {
    var nome by remember { mutableLongStateOf(0L) }
    var observacoes by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    if(exercicio.getImagem().isNotEmpty()){
        nome = exercicio.getNome()
        observacoes = exercicio.getObservacoes()
    }

    Dialog(
        onDismissRequest = {
            onDismiss()
        }

    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = nome.toString(),
                    onValueChange = {
                        nome = it.toLongOrNull() ?: 0L
                    },
                    label = { Text("Nome") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                val imageSelected = PickImageButton()
                OutlinedTextField(
                    value = observacoes,
                    onValueChange = {
                        observacoes = it
                    },
                    label = { Text("Observacoes") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                val context = LocalContext.current
                val contentResolver = context.contentResolver

                Button(
                    onClick = {

                        if (exercicio.getImagem().isEmpty()) {
                            insertExercicio(
                                imageSelected,
                                isLoading,
                                contentResolver,
                                onSubmit,
                                nome,
                                observacoes,
                                setLoading = { isLoading = it }
                            )
                        } else {
                            updateExercicio(
                                imageSelected,
                                isLoading,
                                contentResolver,
                                onSubmit,
                                nome,
                                observacoes,
                                imageName = exercicio.getImageName(),
                                setLoading = { isLoading = it }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Salvar")
                }
            }
        }
        LoadingDialog(isLoading = isLoading)
    }

}

fun updateExercicio(
    imageSelected: Uri?,
    loading: Boolean,
    contentResolver: ContentResolver?,
    onSubmit: (String, Long, Uri, String,Boolean?) -> Unit,
    nome: Long,
    observacoes: String,
    setLoading: (Boolean) -> Unit,
    imageName:String
) {
    setLoading(loading)
    if (imageSelected != null) {
        setLoading(true)
        Storage.updateFile(imageName, uriToByteArray(contentResolver!!, imageSelected)) {
            it.result.storage.downloadUrl.addOnCompleteListener { task ->
                onSubmit(imageName, nome, task.result, observacoes,true)
                setLoading(false)
            }
        }
    }
}

private fun insertExercicio(
    imageSelected: Uri?,
    isLoading: Boolean,
    contentResolver: ContentResolver,
    onSubmit: (String, Long, Uri, String,Boolean?) -> Unit,
    nome: Long,
    observacoes: String,
    setLoading: (Boolean) -> Unit
) {
    setLoading(isLoading)
    if (imageSelected != null) {
        setLoading(true)
        val imageName = UUID.randomUUID().toString()
        Storage.addFile(imageName, uriToByteArray(contentResolver, imageSelected)) {
            it.result.storage.downloadUrl.addOnCompleteListener { task ->
                onSubmit(imageName, nome, task.result, observacoes,null)
                setLoading(false)
            }
        }
    }
}



