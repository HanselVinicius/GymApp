package br.com.viniciusapps.gym_app.infra.utils

import android.content.ContentResolver
import android.net.Uri

import java.io.InputStream


fun uriToByteArray(contentResolver: ContentResolver, uri: Uri): ByteArray {
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val bytes = inputStream?.readBytes() ?: byteArrayOf()
    inputStream?.close()
    return bytes
}

fun urlCleanner(url: String): String {
    val novaUrl = url.replace("/imagens_exercicios/", "/imagens_exercicios%2F")
    return novaUrl
}