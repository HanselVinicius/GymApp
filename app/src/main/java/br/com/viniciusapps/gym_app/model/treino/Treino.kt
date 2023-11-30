package br.com.viniciusapps.gym_app.model.treino

import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import java.sql.Timestamp

class Treino {

    companion object{
        fun create(nome:Long,descricao:String,data:Timestamp,exercicios: ArrayList<Exercicio>):Treino{
            return Treino(nome,descricao,data,exercicios)
        }
    }

    private val nome:Long
    private val descricao:String
    private val data:Timestamp
    private val exercicios:ArrayList<Exercicio>


    fun getNome(): Long {
        return nome
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

    private constructor(nome:Long,descricao:String,data:Timestamp,exercicios:ArrayList<Exercicio>) {
        this.nome = nome
        this.descricao = descricao
        this.data = data
        this.exercicios = exercicios
    }





}