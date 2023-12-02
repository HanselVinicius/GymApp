package br.com.viniciusapps.gym_app.infra.firebase.storage

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.storage

class Storage {

    fun addFile(imageName: String,
        byteArray: ByteArray,
        onCompleteListener: OnCompleteListener<UploadTask.TaskSnapshot>
    ) {

        val dataRef = Firebase.storage("gs://gymapp-f187a.appspot.com").reference.child(
            "imagens_exercicios"
        ).child(imageName)
        dataRef.putBytes(byteArray).addOnCompleteListener(onCompleteListener)
    }

    fun updateFile(imageName: String,
        byteArray: ByteArray,
        onCompleteListener: OnCompleteListener<UploadTask.TaskSnapshot>
    ) {

        val dataRef = Firebase.storage("gs://gymapp-f187a.appspot.com").reference.child(
            "imagens_exercicios"
        ).child(imageName)
        dataRef.putBytes(byteArray).addOnCompleteListener(onCompleteListener)

    }

    fun deleteFile(id:String){
        Firebase.storage("gs://gymapp-f187a.appspot.com").reference.child(
            "imagens_exercicios"
        ).child(id).delete()
    }


}