package br.com.viniciusapps.gym_app.infra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.viniciusapps.gym_app.ui.activity.FormActivity
import br.com.viniciusapps.gym_app.ui.activity.Home
import br.com.viniciusapps.gym_app.ui.activity.LoginComponent
import br.com.viniciusapps.gym_app.ui.activity.RegisterComponent


@Composable
fun SetupNavigation(navController: NavHostController){

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            LoginComponent(navController)
        }
        composable("register"){
            RegisterComponent(navController)
        }
        composable(route="home/{userId}",arguments = listOf(navArgument("userId"){type = NavType.StringType})){it ->
            Home(it.arguments?.getString("userId")?:"",navController)
        }
        composable(route="form"){
            FormActivity()
        }
    }
}