package br.com.viniciusapps.gym_app.model.exercicio

import java.net.URL

class Exercicio {

    companion object{
        fun create(nome:Long, imagem:URL, observacoes:String):Exercicio{
            return Exercicio(nome,imagem,observacoes)
        }
    }

    private var nome:Long
    private var imagem:URL
    private var observacoes:String

    private constructor(nome:Long, imagem:URL, observacoes:String){
        this.nome = nome
        this.imagem = imagem
        this.observacoes = observacoes
    }


}



