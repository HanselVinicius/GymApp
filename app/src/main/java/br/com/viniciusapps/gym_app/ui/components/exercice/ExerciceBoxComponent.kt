package br.com.viniciusapps.gym_app.ui.components.exercice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.viniciusapps.gym_app.infra.firebase.storage.Storage
import br.com.viniciusapps.gym_app.infra.utils.urlCleanner
import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import br.com.viniciusapps.gym_app.ui.components.DefaultCard
import br.com.viniciusapps.gym_app.ui.components.GenericAlertDialog

@Composable
fun ExerciceBox(exercicios: ArrayList<Exercicio>) {
    val dialogCreateState = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var deleteIndex by remember { mutableIntStateOf(0) }
    var exercicio by remember { mutableStateOf(Exercicio.createEmptyExercicio()) }
    if (dialogCreateState.value) {
        FormDialog(onDismiss = {
            dialogCreateState.value = false
        },{ imageName, nome, imagem, observacoes,isUpdate ->
            if(isUpdate == true){
                val index = exercicios.indexOfFirst { it == exercicio }
                if (index != -1) {
                    exercicios[index] = Exercicio(imageName, nome, imagem.toString(), observacoes)
                }
                dialogCreateState.value = false
                return@FormDialog
            }
            exercicios.add(Exercicio(imageName, nome, imagem.toString(), observacoes))
            dialogCreateState.value = false
        },exercicio)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    dialogCreateState.value = true
                }) {
                Text("Adicionar exercicio")
            }
            LazyColumn(content = {
                items(exercicios.size) { index ->
                    val urlCleanner = urlCleanner(exercicios[index].getImagem())
                    DefaultCard(
                        "Nome : ${exercicios[index].getNome()}",
                        "Observações : ${exercicios[index].getObservacoes()}",
                        onClick = {
                            exercicio = exercicios[index]
                            dialogCreateState.value = true
                        },
                        image = urlCleanner,
                        onDelete = {
                            showDialog = true
                            deleteIndex = index
                        }
                    )
                }
            })
        }
        if (showDialog) {
            GenericAlertDialog(
                "Excluir exercicio",
                "Deseja excluir esse exercicio?",
                onConfirm = {
                    showDialog = false
                    exercicios.removeAt(deleteIndex)
                    Storage.deleteFile(exercicios[deleteIndex].getImageName())
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }
    }
}