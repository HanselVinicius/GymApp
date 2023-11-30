package br.com.viniciusapps.gym_app.ui.activity

import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormActivity() {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
//    var data by remember { mutableStateOf(Calendar.getInstance()) }
    var exercicios by remember { mutableStateOf(ArrayList<Exercicio>()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

//            DatePicker(
//                selectedDate = data,
//                onDateChange = { data = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp)
//            )

            // TODO: Add a more complex form for exercicios if needed

            Button(
                onClick = {
                    // TODO: Handle form submission
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Submit")
            }
        }
    }
}