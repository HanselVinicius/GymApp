package br.com.viniciusapps.gym_app.model.treino

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

interface TreinoRepository {

        fun save(treino: Treino,onResult: ()->Unit)

        fun update(treino: Treino,onResult: ()->Unit)

        fun delete(treino: Treino)

        fun getAll(userId:String,onGetCallback:(ArrayList<Treino>)->Unit)
}