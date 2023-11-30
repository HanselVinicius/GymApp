package br.com.viniciusapps.gym_app.infra.firebase.firestore_db


import br.com.viniciusapps.gym_app.model.treino.Treino
import br.com.viniciusapps.gym_app.model.treino.TreinoRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreTreinoRepository(private val firebaseFirestore: FirebaseFirestore) : TreinoRepository{
    override fun save(treino: Treino): Task<DocumentReference> {
       return this.firebaseFirestore.collection(treino::javaClass.name).add(treino)
    }

//    override fun update(treino: Treino) {
//        TODO("Not yet implemented")
//    }
//
//    override fun delete(treino: Treino) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getAll(): ArrayList<Treino> {
//        TODO("Not yet implemented")
//    }


}