package br.com.viniciusapps.gym_app.model.exercicio

import java.net.URL

class Exercicio {

    companion object{
        fun create(nome:Long,imagem:URL,obsercacoes:String):Exercicio{
            return Exercicio(nome,imagem,obsercacoes)
        }
    }

    private var nome:Long
    private var imagem:URL
    private var obsercacoes:String

    private constructor(nome:Long,imagem:URL,obsercacoes:String){
        this.nome = nome
        this.imagem = imagem
        this.obsercacoes = obsercacoes
    }


}



