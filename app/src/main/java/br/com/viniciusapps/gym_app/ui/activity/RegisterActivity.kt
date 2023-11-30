package br.com.viniciusapps.gym_app.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.viniciusapps.gym_app.infra.firebase.authentication.Authentication
import br.com.viniciusapps.gym_app.ui.theme.Gym_appTheme
import br.com.viniciusapps.gym_app.ui.components.AuthComponent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class RegisterActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gym_appTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    RegisterComponent()
                }

            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterComponent(navController: NavController) {
    val scope = rememberCoroutineScope()
    val snac = remember { SnackbarHostState() }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snac)
    }) { _ ->
        AuthComponent(text = "Registro", onClick = {username,password ->
            run {
                try {
                    Authentication.create(
                        FirebaseAuth.getInstance(),
                        username,
                        password
                    )
                        .register()
                }catch (ex:RuntimeException){
                    scope.launch {
                        snac.showSnackbar("Entradas Inválidas")
                    }
                }catch (ex:NullPointerException){
                    scope.launch {
                        snac.showSnackbar("Entradas Inválidas")
                    }
                }
            }
        },tela = RegisterActivity::class.java, navController = navController)
    }

}


//@Preview(showBackground = true)
//@Composable
//fun RegisterPreview() {
//    Gym_appTheme {
//        RegisterComponent()
//    }
//}
