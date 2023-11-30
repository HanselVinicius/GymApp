package br.com.viniciusapps.gym_app.infra.validation.input



class InputNotEmptyValidation: InputValidators {
    override fun validate(vararg input: String) {

        for (i in input){
            if (i.isBlank() || i.isEmpty()){
                throw RuntimeException("Entradas Inválidas")
            }
        }
    }


}