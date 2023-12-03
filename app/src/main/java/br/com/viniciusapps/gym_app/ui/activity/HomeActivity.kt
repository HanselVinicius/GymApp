package br.com.viniciusapps.gym_app.ui.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.viniciusapps.gym_app.infra.firebase.firestore_db.FirestoreTreinoRepositoryImpl
import br.com.viniciusapps.gym_app.infra.firebase.storage.Storage
import br.com.viniciusapps.gym_app.model.treino.Treino
import br.com.viniciusapps.gym_app.ui.components.DefaultCard
import br.com.viniciusapps.gym_app.ui.components.GenericAlertDialog
import br.com.viniciusapps.gym_app.ui.theme.BlueStrong
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(userId: String,navHostController: NavHostController) {

    Scaffold(
        content = {
            CardCreate(modifier = Modifier.padding(vertical = 16.dp),userId,navHostController)
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = BlueStrong,
                onClick = {
                    navHostController.navigate("form/${userId}")
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adcionar treino")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
}


@Preview
@Composable
fun HomePreview() {
    Home("1", rememberNavController())
}

@Composable
private fun CardCreate(modifier: Modifier, userId: String,nav:NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var deleteIndex by remember { mutableIntStateOf(0) }

    var listDeTreinos by remember { mutableStateOf<List<Treino>>(emptyList()) }

    val firestoreRepository = remember { FirestoreTreinoRepositoryImpl(FirebaseFirestore.getInstance()) }

    LaunchedEffect(userId) {
         firestoreRepository.getAll(userId) {
            listDeTreinos = it
        }
    }
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(listDeTreinos) { item ->
                val formattedDateTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    .format(item.getData().toDate())
                DefaultCard(
                    "Nome:${item.getNome()}",
                    "Descrição:${item.getDescricao()}",
                    "Data:${formattedDateTime}",
                    onClick = {
                        nav.navigate("form/${userId}?treino=${Gson().toJson(item)}")
                    },
                    image = "",
                    onDelete = {
                        showDialog = true
                        deleteIndex = listDeTreinos.indexOf(item)

                    }
                )
            }
        }

        if (showDialog) {
            GenericAlertDialog(
                "Excluir treino",
                "Deseja excluir o treino?",
                onConfirm = {
                    val itemAExcluir = listDeTreinos[deleteIndex]
                    listDeTreinos = listDeTreinos.toMutableList().apply {
                        removeAt(deleteIndex)
                    }
                    firestoreRepository.delete(itemAExcluir)
                    itemAExcluir.getExercicios().forEach { exercicio ->
                        Storage.deleteFile(exercicio.getImageName())
                    }
                    showDialog = false
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }
    }
}




