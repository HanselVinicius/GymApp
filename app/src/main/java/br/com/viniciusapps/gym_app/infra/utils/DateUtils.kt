package br.com.viniciusapps.gym_app.infra.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun converterParaTimestampFirebase(data: String, hora: String): Timestamp {
    val dataHoraString = "$data $hora"
    val formatoEntrada = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    try {
        val dataHoraDate: Date = formatoEntrada.parse(dataHoraString) ?: throw Exception("Erro ao fazer parse da data e hora")

        return Timestamp(dataHoraDate)
    } catch (e: Exception) {
        throw e
    }
}