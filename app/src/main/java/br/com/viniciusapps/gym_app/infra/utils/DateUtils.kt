package br.com.viniciusapps.gym_app.infra.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

fun converterParaTimestampFirebase(data: String, hora: String): Timestamp {
    var dataHoraString = "$data $hora"
    if (data.isEmpty() && hora.isEmpty()) {
        return Timestamp.now()
    }

    if (data.isEmpty()) {
        dataHoraString =
            "${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())} $hora"
    }

    if (hora.isEmpty()) {
        dataHoraString = "$data ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())}"
    }


    val formatoEntrada = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    val dataHoraDate: Date = formatoEntrada.parse(dataHoraString)
    return Timestamp(dataHoraDate)
}