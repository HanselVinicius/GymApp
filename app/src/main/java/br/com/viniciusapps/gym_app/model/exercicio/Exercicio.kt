package br.com.viniciusapps.gym_app.model.exercicio


data class Exercicio (
    private var imageName:String,
    private var nome: Long,
    private var imagem: String,
    private var observacoes: String
) {

    companion object{
         fun deserializeExercicios(exerciciosList: List<Map<String, Any>>): ArrayList<Exercicio> {
            val exercicios = ArrayList<Exercicio>()

            for (map in exerciciosList) {
                val imageName = map["imageName"] as String
                val nome = map["nome"] as Long
                val imagem = map["imagem"] as String
                val observacoes = map["observacoes"] as String

                val exercicio = Exercicio(imageName,nome, imagem, observacoes)
                exercicios.add(exercicio)
            }

            return exercicios
        }


        fun createEmptyExercicio(): Exercicio {
            return Exercicio("",0L,"","")
        }

    }

    fun getImageName():String{
        return this.imageName
    }

    fun getNome():Long{
        return this.nome
    }

    fun getImagem():String{
        return this.imagem
    }

    fun getObservacoes():String{
        return this.observacoes
    }


}



