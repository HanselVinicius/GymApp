package br.com.viniciusapps.gym_app.model.treino

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

interface TreinoRepository {

        fun save(treino: Treino):Task<DocumentReference>

//        fun update(treino: Treino)
//
//        fun delete(treino: Treino)
//
        fun getAll(): ArrayList<Treino>
}