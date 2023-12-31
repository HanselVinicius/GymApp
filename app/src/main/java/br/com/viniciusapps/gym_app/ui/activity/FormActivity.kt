package br.com.viniciusapps.gym_app.ui.activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.viniciusapps.gym_app.infra.firebase.firestore_db.FirestoreTreinoRepositoryImpl

import br.com.viniciusapps.gym_app.infra.utils.converterParaTimestampFirebase
import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import br.com.viniciusapps.gym_app.model.treino.Treino
import br.com.viniciusapps.gym_app.ui.components.LoadingDialog
import br.com.viniciusapps.gym_app.ui.components.exercice.ExerciceBox
import br.com.viniciusapps.gym_app.ui.theme.BlueStrong
import com.google.firebase.firestore.FirebaseFirestore
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection


@Composable
fun FormActivity(navController: NavController, userId: String, treino: Treino? = null) {
    val nome by remember { mutableStateOf(treino?.getNome() ?: "") }
    val descricao by remember { mutableStateOf(treino?.getDescricao() ?: "") }
    val exercicios by remember {
        mutableStateOf(treino?.getExercicios() ?: ArrayList())
    }
    var isNomeError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

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
            val nameAndDescription =
                TextFields(nome.toString(), descricao, isNomeError)
            val dateAndTimeValue = DateSelector()
            Button(
                onClick = {
                    if (nameAndDescription.first.isEmpty()) {
                        isNomeError = true
                        return@Button
                    }
                    if (treino == null) {
                        saveTreino(
                            treino,
                            userId,
                            nameAndDescription,
                            dateAndTimeValue,
                            exercicios,
                            navController,
                            setLoading = { isLoading = it }
                        )
                    } else {
                        updateTreino(
                            treino,
                            userId,
                            nameAndDescription,
                            dateAndTimeValue,
                            exercicios,
                            navController,
                            setLoading = { isLoading = it }
                        )

                    }

                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Salvar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            ExerciceBox(exercicios)

        }

    }
    LoadingDialog(isLoading = isLoading)

}


private fun updateTreino(
    treino: Treino?,
    userId: String,
    nameAndDescription: Pair<String, String>,
    dateAndTimeValue: Pair<String, String>,
    exercicios: java.util.ArrayList<Exercicio>,
    navController: NavController,
    setLoading: (Boolean) -> Unit
) {
    setLoading(true)

    val treinoVal = Treino(
        treino?.getDocumentId() ?: "",
        userId,
        nameAndDescription.first.trim().toLong(),
        nameAndDescription.second,
        converterParaTimestampFirebase(dateAndTimeValue.first, dateAndTimeValue.second),
        exercicios,
        true
    )


    FirestoreTreinoRepositoryImpl(FirebaseFirestore.getInstance()).update(treinoVal) {
        setLoading(false)
        navController.popBackStack()

    }
}


private fun saveTreino(
    treino: Treino?,
    userId: String,
    nameAndDescription: Pair<String, String>,
    dateAndTimeValue: Pair<String, String>,
    exercicios: java.util.ArrayList<Exercicio>,
    navController: NavController,
    setLoading: (Boolean) -> Unit
) {
    setLoading(true)

    val treinoVal = Treino(
        treino?.getDocumentId() ?: "",
        userId,
        nameAndDescription.first.trim().toLong(),
        nameAndDescription.second,
        converterParaTimestampFirebase(dateAndTimeValue.first, dateAndTimeValue.second),
        exercicios,
        true
    )

    FirestoreTreinoRepositoryImpl(FirebaseFirestore.getInstance()).save(treinoVal) {
        setLoading(false)
        navController.popBackStack()
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFields(
    nome: String,
    descricao: String,
    isNomeError: Boolean,
): Pair<String, String> {
    var nameValue by remember { mutableStateOf(nome) }
    var descriptionValue by remember { mutableStateOf(descricao) }

    TextField(
        value = nameValue,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        onValueChange = {
            nameValue = it
        },
        isError = isNomeError,
        label = { Text("Nome") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
    TextField(
        value = descriptionValue,
        onValueChange = {
            descriptionValue = it
        },
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




