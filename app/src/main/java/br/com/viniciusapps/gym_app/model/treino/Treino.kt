package br.com.viniciusapps.gym_app.model.treino

import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import br.com.viniciusapps.gym_app.model.exercicio.Exercicio.Companion.deserializeExercicios
import com.google.firebase.Timestamp

data class Treino(
    private var documentId:String,
    private var userId: String,
    private var nome: Long,
    private var descricao: String,
    private var data: Timestamp,
    private var exercicios: ArrayList<Exercicio>,
    private var isActive:Boolean
) {


    companion object {
        fun fromFirebaseCreate(
            documentId: String,
            userId: String,
            nome: Long,
            descricao: String,
            data: Timestamp,
            exercicios: List<Map<String, Any>>,
            isActive: Boolean
        ): Treino {
            return Treino(
                documentId,
                userId,
                nome,
                descricao,
                data,
                deserializeExercicios(exercicios),
                isActive
            )
        }

        fun createEmptyTreino(userId: String): Treino {
            return Treino("",userId, 0, "", Timestamp.now(), ArrayList(),true)
        }

    }


    fun isActive():Boolean{
        return isActive
    }

    fun getDocumentId():String{
        return documentId
    }

    fun getNome(): Long {
        return nome
    }

    fun getUserId(): String {
        return userId
    }

    fun getDescricao(): String {
        return descricao
    }

    fun getData(): Timestamp {
        return data
    }

    fun getExercicios(): ArrayList<Exercicio> {
        return exercicios
    }

    fun setExercicios(exercicios: ArrayList<Exercicio>) {
        this.exercicios = exercicios
    }

    override fun    toString(): String {
        return "Treino(userId='$userId', nome=$nome, descricao='$descricao', data=$data, exercicios=$exercicios)"
    }


}