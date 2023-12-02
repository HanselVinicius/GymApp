package br.com.viniciusapps.gym_app.infra.firebase.storage

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.storage

class Storage {


     fun addFile(
        byteArray: ByteArray,
        onCompleteListener: OnCompleteListener<UploadTask.TaskSnapshot>
    ) {
        Firebase.storage("gs://gymapp-f187a.appspot.com").reference.child("teste")
            .putBytes(byteArray).addOnCompleteListener(onCompleteListener)
    }
}