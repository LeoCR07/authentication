package com.example.authentication.ui.Navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authentication.Presentation.LoginScreen
import com.example.authentication.ui.Presentation.AccountScreen
import com.example.authentication.ui.Presentation.SingUpScreen
import com.example.authentication.ui.ViewModels.LoginViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    startDestination: String,
) {


    NavHost(
        navController = navController,
        startDestination = startDestination
      //  startDestination = LoginRoutes.Login.name
    ) {

        composable(route = LoginRoutes.Login.name) {
            LoginScreen(
                loginViewModel = loginViewModel,
                ClickSingUpFacebook = {},
                ClickSingUpMicrosoft = {},
                NavToAccountScreen = {
                    navController.navigate(HomeRoutes.Account.name) {
                        launchSingleTop = true

                        popUpTo(route = LoginRoutes.Login.name) {
                            inclusive = true
                        }
                    }
                },
                ClickForgotPassword = {},
                NavToSingUpScreen = {
                    navController.navigate(LoginRoutes.SingUp.name) {
                        launchSingleTop = true
                    }
                })

        }

        composable(route = LoginRoutes.SingUp.name){
            SingUpScreen(
                loginViewModel = loginViewModel,
                onNavToAccount = {
                    navController.navigate(HomeRoutes.Account.name) {
                        launchSingleTop = true

                        popUpTo(route = LoginRoutes.SingUp.name) {
                            inclusive = true
                        }
                    }
                },
                ClickSingUp = {})
        }

        composable(route = HomeRoutes.Account.name){
            AccountScreen(
                loginViewModel = loginViewModel,
                onNavToLogin = {
                    navController.navigate(LoginRoutes.Login.name){
                        launchSingleTop = true

                        popUpTo(route = HomeRoutes.Account.name) {
                            inclusive = true
                        }
                    }
                })
        }

    }
}
