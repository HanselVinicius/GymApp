package br.com.viniciusapps.gym_app.ui.activity

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.viniciusapps.gym_app.infra.firebase.firestore_db.FirestoreTreinoRepositoryImpl
import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import br.com.viniciusapps.gym_app.model.treino.Treino
import br.com.viniciusapps.gym_app.ui.components.DefaultCard
import br.com.viniciusapps.gym_app.ui.components.GenericAlertDialog
import br.com.viniciusapps.gym_app.ui.components.exercice.FormDialog
import br.com.viniciusapps.gym_app.ui.theme.BlueStrong
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.net.URL
import java.sql.Date
import java.time.LocalDateTime


@Composable
fun FormActivity() {
    val nome by remember { mutableStateOf("") }
    val descricao by remember { mutableStateOf("") }
    val exercicios by remember { mutableStateOf(ArrayList<Exercicio>()) }

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
            val nameAndDescription = TextFields(nome, descricao)
            val dateAndTimeValue = DateSelector()
            val pattern = "yyyy-MM-dd'T'HH:mm:ss"
            Button(
                onClick = {
                    val treino = Treino.create(
                        nameAndDescription.first.toLong(),
                        nameAndDescription.second,
                        Timestamp(Date.valueOf(dateAndTimeValue.first)),
                        exercicios
                    )
                    FirestoreTreinoRepositoryImpl(FirebaseFirestore.getInstance()).save(treino)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Salvar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            ExerciceBox(exercicios)

        }

    }
}

@Composable
fun ExerciceBox(exercicios: ArrayList<Exercicio>) {
    val dialogState = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var deleteIndex by remember { mutableIntStateOf(0) }
    if (dialogState.value) {
        FormDialog(onDismiss = { dialogState.value = false }) { nome, imagem, observacoes ->
            exercicios.add(Exercicio.create(nome, URL("http://teste.com.br"), observacoes))
            dialogState.value = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    dialogState.value = true
                }) {
                Text("Adicionar exercicio")
            }
            LazyColumn(content = {
                items(exercicios.size) { index ->
                    DefaultCard(
                        "Nome : ${exercicios[index].getNome()}",
                        "Observações : ${exercicios[index].getObservacoes()}",
                        onClick = {},
                        image = exercicios[index].getImagem().toString(),
                        onDelete = {
                            showDialog = true
                            deleteIndex = index
                            Log.d("HomeActivity", "onConfirm: ")
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
                    Log.d("HomeActivity", "onConfirm: ")
                },
                onDismiss = {
                    showDialog = false
                    exercicios.removeAt(deleteIndex)
                }
            )
        }
    }
}



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFields(nome: String, descricao: String): Pair<String, String> {
    var nameValue by remember { mutableStateOf(nome) }
    var descriptionValue by remember { mutableStateOf(descricao) }

    TextField(
        value = nameValue,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        onValueChange = { nameValue = it },
        label = { Text("Nome") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
    TextField(
        value = descriptionValue,
        onValueChange = { descriptionValue = it },
        label = { Text("Descrição") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
    return Pair(nameValue, descriptionValue)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(): Pair<String, String> {
    val calendarState = rememberSheetState()
    val clockState = rememberSheetState()
    var selectedDate by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { date ->
            selectedDate = date.toString()
        })
    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes { hour, minute -> time = "$hour:$minute" })
    Row {
        OutlinedButton(
            modifier = Modifier.padding(end = 20.dp),
            border = BorderStroke(1.dp, BlueStrong),
            onClick = { calendarState.show() },

            ) {
            Text(if (selectedDate == "") "Selecionar data" else selectedDate)
        }

        OutlinedButton(
            border = BorderStroke(1.dp, BlueStrong),
            onClick = { clockState.show() },

            ) {
            Text(if (time == "") "Selecionar hora" else time)
        }
    }

    return Pair(selectedDate, time)
}

@Preview
@Composable
fun FormPreview() {
    FormActivity()
}


