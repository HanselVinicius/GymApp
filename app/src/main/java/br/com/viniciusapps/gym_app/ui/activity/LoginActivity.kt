package br.com.viniciusapps.gym_app.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.navigation.compose.rememberNavController
import br.com.viniciusapps.gym_app.infra.firebase.authentication.Authentication
import br.com.viniciusapps.gym_app.infra.navigation.SetupNavigation
import br.com.viniciusapps.gym_app.ui.theme.Gym_appTheme
import br.com.viniciusapps.gym_app.ui.components.AuthComponent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gym_appTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SetupNavigation(navController)
                }
            }
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponent(navController: NavController) {
    val scope = rememberCoroutineScope()
    val snac = remember { SnackbarHostState() }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snac)
    }) { _ ->
        Column(modifier = Modifier.fillMaxHeight()) {
            AuthComponent(text = "Login", onClick = { username, password ->
                run {
                    try {
                        val auth = Authentication.create(FirebaseAuth.getInstance(), username, password)
                        auth.login()
                    }catch (ex: RuntimeException){
                        scope.launch {
                            snac.showSnackbar("Entradas Inválidas")
                        }
                    }catch (ex:NullPointerException){
                        scope.launch {
                            snac.showSnackbar("Entradas Inválidas")
                        }
                    }
                }
            }, navController = navController)
        }
    }

}





//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    Gym_appTheme {
//        LoginComponent()
//    }
//}