package com.example.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authentication.ui.Foundation.LoginScreen.CustomFacebookButton
import com.example.authentication.ui.Navigation.HomeRoutes
import com.example.authentication.ui.Navigation.LoginRoutes
import com.example.authentication.ui.Navigation.Navigation
import com.example.authentication.ui.ViewModels.LoginViewModel
import com.example.authentication.ui.theme.AuthenticationTheme
import com.facebook.FacebookSdk

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var startDestination = LoginRoutes.Login.name
                    val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)


                    if (loginViewModel?.hasUser){
                        startDestination = HomeRoutes.Account.name
                    }



                    //FacebookSdk.sdkInitialize(applicationContext)

                    Navigation(
                        loginViewModel = loginViewModel,
                        startDestination = startDestination)

                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Botonesta() {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 0.dp, 15.dp, 0.dp)
            .height(60.dp),
        value = "loginUiState?.password ?: ",
        onValueChange = {  },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
            )
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "Password",
                color = Color.Black.copy(alpha = 0.2f))
        },
        visualTransformation = if(true) VisualTransformation.None else PasswordVisualTransformation(),
        //isError = isError
        isError = true,
        supportingText = {
            Text(text = "Aqui va el error", color = Color.Black, fontSize = 20.sp)
        },
        trailingIcon = {
            Icon(
                imageVector = if(true) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                modifier = Modifier
                    .clickable { },
                contentDescription = null,
                tint = Color.Black
            )

        }
    )
}
