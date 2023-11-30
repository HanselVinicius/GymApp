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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import br.com.viniciusapps.gym_app.model.treino.Treino
import br.com.viniciusapps.gym_app.ui.components.DefaultCard
import br.com.viniciusapps.gym_app.ui.theme.BlueStrong
import java.net.URL
import java.sql.Timestamp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(userId: String,navHostController: NavHostController) {
    Scaffold(
        content = {
            CardCreate(modifier = Modifier.padding(vertical = 16.dp))
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = BlueStrong,
                onClick = {
                    navHostController.navigate("form")
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
private fun CardCreate(modifier: Modifier) {
    var listDeTreinos by remember {
        mutableStateOf(
            listOf(
                Treino.create(
                    nome = System.currentTimeMillis(),
                    descricao = "Treino para hoje",
                    data = Timestamp(System.currentTimeMillis()),
                    exercicios = arrayListOf(
                        Exercicio.create(
                            nome = System.currentTimeMillis(),
                            imagem = URL("https://example.com/image.jpg"),
                            observacoes = "Realizar 3 séries de 15 repetições"
                        )
                    )
                ),
            )
        )
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
                DefaultCard("Nome:${item.getNome()}", "Descrição:${item.getDescricao()}","Data:${item.getData()}")

            }
        }
    }

}

