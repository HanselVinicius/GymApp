package br.com.viniciusapps.gym_app.infra.firebase.authentication


import android.util.Log
import br.com.viniciusapps.gym_app.infra.validation.input.InputNotEmptyValidation
import br.com.viniciusapps.gym_app.infra.validation.input.InputNullValidation
import br.com.viniciusapps.gym_app.infra.validation.input.InputValidators
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Authentication {


    companion object{
        fun create(firebaseInstance: FirebaseAuth, username: String, password: String): Authentication {
            validate(username, password)
            return Authentication(firebaseInstance, username, password)
        }

        private fun validate(username: String, password: String) {
            val emptyCheck = InputNotEmptyValidation()
            val nullCheck = InputNullValidation()
            val hashMap = HashMap<String, InputValidators>()
            hashMap[username] = emptyCheck
            hashMap[password] = emptyCheck
            hashMap[username] = nullCheck
            hashMap[password] = nullCheck
            for (i in hashMap) {
                i.value.validate(i.key)
            }
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


    fun register() {
        firebaseInstance.createUserWithEmailAndPassword(this.username.trim(), this.password.trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("TAG", "register: teste registro " +task.result?.user )
                } else {

                }
            }
    }

}