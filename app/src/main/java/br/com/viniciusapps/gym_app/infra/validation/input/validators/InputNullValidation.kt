package br.com.viniciusapps.gym_app.infra.validation.input.validators

import br.com.viniciusapps.gym_app.infra.validation.input.InputValidators


class InputNullValidation: InputValidators {
    override fun validate(vararg input: String) {
        for (i in input){

            if (i == null){
                throw NullPointerException( "Entradas Inválidas")
            }
        }
    }
}