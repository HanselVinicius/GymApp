package br.com.viniciusapps.gym_app.infra.firebase.authentication


import android.util.Log
import br.com.viniciusapps.gym_app.infra.validation.input.validators.InputNotEmptyValidation
import br.com.viniciusapps.gym_app.infra.validation.input.validators.InputNullValidation
import br.com.viniciusapps.gym_app.infra.validation.input.InputValidators
import br.com.viniciusapps.gym_app.infra.validation.input.validate
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Authentication {


    companion object{
        fun create(firebaseInstance: FirebaseAuth, username: String, password: String): Authentication {
            validate(username, password)
            return Authentication(firebaseInstance, username, password)
        }


    }

    private val firebaseInstance:FirebaseAuth
    private var username: String
    private var password: String

    private constructor(firebaseInstance: FirebaseAuth, username: String, password: String) {
        this.firebaseInstance = firebaseInstance
        this.username = username
        this.password = password
    }



    fun login(callbackFun:OnCompleteListener<AuthResult>) {
        firebaseInstance.signInWithEmailAndPassword(this.username.trim(), this.password.trim())
            .addOnCompleteListener(callbackFun)
    }


    fun register(callbackFun:OnCompleteListener<AuthResult>) {
        firebaseInstance.createUserWithEmailAndPassword(this.username.trim(), this.password.trim())
            .addOnCompleteListener(callbackFun)
    }

}