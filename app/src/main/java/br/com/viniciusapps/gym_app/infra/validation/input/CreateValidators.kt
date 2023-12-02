package br.com.viniciusapps.gym_app.infra.validation.input

import br.com.viniciusapps.gym_app.infra.validation.input.validators.InputNotEmptyValidation
import br.com.viniciusapps.gym_app.infra.validation.input.validators.InputNullValidation

fun validate(vararg args: String) {
    val emptyCheck = InputNotEmptyValidation()
    val nullCheck = InputNullValidation()
    val hashMap = HashMap<String, InputValidators>()
    for (i in args) {
        hashMap[i] = emptyCheck
        hashMap[i] = nullCheck
    }
    for (i in hashMap) {
        i.value.validate(i.key)
    }
}