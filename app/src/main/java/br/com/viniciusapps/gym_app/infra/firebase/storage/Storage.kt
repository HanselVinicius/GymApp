package br.com.viniciusapps.gym_app.infra.firebase.storage

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

class Storage {


    private fun addFile() {
        Firebase.storage("").reference.child("").putBytes(ByteArray(0)).addOnCompleteListener(
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("TAG", "addFile: " + task.result?.storage?.downloadUrl)
                } else {

                }
            })
    }
}