package br.com.viniciusapps.gym_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.viniciusapps.gym_app.ui.theme.OrangeStrong
import coil.compose.AsyncImage


@Composable
fun DefaultCard(vararg textos: String) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = OrangeStrong
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    for (texto in textos) {
                        Text(
                            text = texto,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(200.dp))

                AsyncImage(
                    model = "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png",
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)

                )
            }
        }
    }
}


//@Composable
//fun ExercicioCard(exercicio: Exercicio) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        elevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Nome do Exercício: ${exercicio.nome}",
//                style = MaterialTheme.typography.body1,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            Text(
//                text = "Imagem: ${exercicio.imagem}",
//                style = MaterialTheme.typography.body1,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            Text(
//                text = "Observações: ${exercicio.observacoes}",
//                style = MaterialTheme.typography.body1,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//        }
//    }
//}

