package br.com.viniciusapps.gym_app.model.treino

import java.sql.Timestamp

class Treino {

    companion object{
        fun create(nome:Long,descricao:String,data:Timestamp):Treino{
            return Treino(nome,descricao,data)
        }
    }

    private var nome:Long

    private var descricao:String

    private var data:Timestamp
    private constructor(nome:Long,descricao:String,data:Timestamp) {
        this.nome = nome
        this.descricao = descricao
        this.data = data
    }



}