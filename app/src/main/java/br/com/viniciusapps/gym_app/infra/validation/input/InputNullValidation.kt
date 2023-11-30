package br.com.viniciusapps.gym_app.infra.validation.input


class InputNullValidation: InputValidators {
    override fun validate(vararg input: String) {
        for (i in input){

            if (i == null){
                throw NullPointerException( "Entradas Inválidas")
            }
        }
    }
}