package br.com.viniciusapps.gym_app.model.treino

import br.com.viniciusapps.gym_app.model.exercicio.Exercicio
import com.google.firebase.Timestamp

class Treino {

    companion object{
        fun create(nome:Long,descricao:String,data:Timestamp,exercicios: ArrayList<Exercicio>):Treino{
            return Treino(nome,descricao,data,exercicios)
        }
    }

    private var nome:Long =0L
    private lateinit  var descricao:String
    private lateinit var data: Timestamp
    private lateinit var exercicios:ArrayList<Exercicio>


    constructor(){

    }

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