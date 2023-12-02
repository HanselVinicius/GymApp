package br.com.viniciusapps.gym_app.infra.firebase.firestore_db


import android.util.Log
import br.com.viniciusapps.gym_app.model.treino.Treino
import br.com.viniciusapps.gym_app.model.treino.TreinoRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreTreinoRepositoryImpl(private val firebaseFirestore: FirebaseFirestore) : TreinoRepository{
    override fun save(treino: Treino,onResult: ()->Unit) {
        this.firebaseFirestore.collection("Treino").add(treino).addOnCompleteListener {
            onResult()
        }
    }

    override fun update(treino: Treino,onResult: ()->Unit) {
        this.firebaseFirestore.collection("Treino").document(treino.getDocumentId()).set(treino).addOnCompleteListener {
            onResult()
        }
    }

    override fun delete(treino: Treino) {
        this.firebaseFirestore.collection("Treino").document(treino.getDocumentId()).delete()
    }

override fun getAll(userId: String, onGetCallback:(ArrayList<Treino>)->Unit) {
    val listDeTreinos = ArrayList<Treino>()
    this.firebaseFirestore.collection("Treino").whereEqualTo("userId", userId).get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val treino = Treino.fromFirebaseCreate(
                    document.id,
                    document.data["userId"] as String,
                    document.data["nome"] as Long,
                    document.data["descricao"] as String,
                    document.data["data"] as Timestamp,
                    document.data["exercicios"] as List<Map<String, Any>>,
                    document.data["active"] as Boolean)
                listDeTreinos.add(treino)
            }
            onGetCallback(listDeTreinos)
            Log.d("TAG", "getAll: data fetched $listDeTreinos " )
        }
}






}