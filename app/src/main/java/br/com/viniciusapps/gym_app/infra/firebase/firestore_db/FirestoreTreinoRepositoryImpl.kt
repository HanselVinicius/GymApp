package br.com.viniciusapps.gym_app.infra.firebase.firestore_db


import android.util.Log
import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import br.com.viniciusapps.gym_app.model.treino.Treino
import br.com.viniciusapps.gym_app.model.treino.TreinoRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URL

class FirestoreTreinoRepositoryImpl(private val firebaseFirestore: FirebaseFirestore) : TreinoRepository{
    override fun save(treino: Treino): Task<DocumentReference> {
       return this.firebaseFirestore.collection("Treino").add(treino)
    }

//    override fun update(treino: Treino) {
//        TODO("Not yet implemented")
//    }
//
//    override fun delete(treino: Treino) {
//        TODO("Not yet implemented")
//    }
//
    override fun getAll(): ArrayList<Treino> {
        val listDeTreinos = ArrayList<Treino>()
        this.firebaseFirestore.collection(Treino::javaClass.name).get().addOnSuccessListener { result ->
            for (document in result) {
                val treino = document.toObject(Treino::class.java)
                listDeTreinos.add(treino)
            }
        }.addOnFailureListener { exception ->
            Log.w("FirestoreTreinoRepositoryImpl", "Error getting documents.", exception)
        }
        return listDeTreinos
    }




}