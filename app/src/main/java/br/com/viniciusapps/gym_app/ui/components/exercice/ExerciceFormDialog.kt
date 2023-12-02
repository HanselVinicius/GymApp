package br.com.viniciusapps.gym_app.ui.components.exercice


import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import br.com.viniciusapps.gym_app.ui.components.LoadingDialog
import br.com.viniciusapps.gym_app.ui.components.PickImageButton
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDialog(
    onDismiss: () -> Unit,
    onSubmit: (String,Long, Uri, String) -> Unit
) {
    var nome by remember { mutableLongStateOf(0L) }
    var observacoes by remember { mutableStateOf("") }
    var isNomeError by remember { mutableStateOf(false) }
    var isObservacoesError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        content = {

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
                            isNomeError = it.toLongOrNull() == null
                        },
                        label = { Text("Nome") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        isError = isNomeError,
                        trailingIcon = {
                            if (isNomeError) {
                                Icon(
                                    imageVector = Icons.Outlined.Error,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    val imageSelected = PickImageButton()
                    OutlinedTextField(
                        value = observacoes,
                        onValueChange = {
                            observacoes = it
                            isObservacoesError = it.isEmpty()
                        },
                        label = { Text("Observacoes") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        isError = isObservacoesError,
                        trailingIcon = {
                            if (isObservacoesError) {
                                Icon(
                                    imageVector = Icons.Outlined.Error,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    val contentResolver = LocalContext.current.contentResolver

                    Button(
                        onClick = {
                            if (!validateFields(nome, imageSelected.toString(), observacoes)) {
                                return@Button
                            }
                            if (imageSelected != null) {
                                isLoading = true
                                val imageName = UUID.randomUUID().toString()
                                Storage().addFile(imageName,uriToByteArray(contentResolver, imageSelected)) {
                                    it.result.storage.downloadUrl.addOnCompleteListener { task ->
                                        onSubmit(imageName,nome, task.result, observacoes)
                                        isLoading = false
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Salvar")
                    }
                }
            }
            LoadingDialog(isLoading =  isLoading)
        }

    )

}





fun validateFields(
    nome: Long,
    imagem: String,
    observacoes: String
): Boolean {
    try {
        validate(nome.toString(), imagem, observacoes)
    } catch (e: RuntimeException) {
        return false
    } catch (e: NullPointerException) {
        return false
    }
    return true
}
