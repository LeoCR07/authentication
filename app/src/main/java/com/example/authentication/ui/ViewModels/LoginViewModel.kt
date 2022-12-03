package com.example.authentication.ui.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.data.models.LoginUiState
import com.example.authentication.data.repository.Authrepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.launch

class LoginViewModel( private val repository: Authrepository = Authrepository() ) : ViewModel() {

    val currentUser = repository.currentUser
    val userId = repository.getUserId()
    val username = repository.getUserName()
    val useremail = repository.getUserEmail()
    val userphoto = repository.getUserPhoto()


    val stars :MutableState<Int> = mutableStateOf(1)

    val hasUser: Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onUserNameChange(userName: String) {
        loginUiState = loginUiState.copy(userName = userName)
    }

    fun onPasswordNameChange(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }

    fun onUserNameChangeSignup(userName: String) {
        loginUiState = loginUiState.copy(userEmailSignUp = userName)
    }

    fun onPasswordChangeSignup(password: String) {
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordChange(password: String) {
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }

    private fun validateLoginForm() =
        loginUiState.userName.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSignupForm() =
        loginUiState.userEmailSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmPasswordSignUp.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignupForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }

            if(loginUiState.passwordSignUp.length < 8){
                throw IllegalArgumentException("password need to have more than 8 words")
            }
            loginUiState = loginUiState.copy(isLoading = true)

            if (loginUiState.passwordSignUp != loginUiState.confirmPasswordSignUp
            ) {
                throw IllegalArgumentException(
                    "Password do not match"
                )
            }

            //loginUiState = loginUiState.copy(signUpError = null)

            repository.createUser(
                loginUiState.userEmailSignUp,
                loginUiState.passwordSignUp
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                } else {
                    /*
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()*/
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }

            }


        } catch (e: Exception) {
           // loginUiState = loginUiState.copy(signUpError = e.localizedMessage+" mistake")
            e.printStackTrace()
            println("Este es el error ${e.message}")

            var error = ""

            if("The email address is already in use by another account." == e.message){

                loginUiState = loginUiState.copy(signUpErrorEmail = e.localizedMessage)
                loginUiState = loginUiState.copy(signUpErrorPassword = null)
                loginUiState = loginUiState.copy(signUpErrorConfirPassword = null)
            }
            if("email and password can not be empty" == e.message){
                loginUiState = loginUiState.copy(signUpErrorEmail = e.localizedMessage)
                loginUiState = loginUiState.copy(signUpErrorPassword = e.localizedMessage)
                loginUiState = loginUiState.copy(signUpErrorConfirPassword = e.localizedMessage)
            }

            if("password need to have more than 8 words" == e.message){

                loginUiState = loginUiState.copy(signUpErrorEmail = null)
                loginUiState = loginUiState.copy(signUpErrorPassword = e.localizedMessage)
                loginUiState = loginUiState.copy(signUpErrorConfirPassword = e.localizedMessage)
            }

            if("The email address is badly formatted." == e.message){

                loginUiState = loginUiState.copy(signUpErrorEmail = e.localizedMessage)
                loginUiState = loginUiState.copy(signUpErrorPassword = null)
                loginUiState = loginUiState.copy(signUpErrorConfirPassword = null)
            }

            if("Password do not match" == e.message){
                loginUiState = loginUiState.copy(signUpErrorEmail = null)
                loginUiState = loginUiState.copy(signUpErrorPassword = e.localizedMessage)
                loginUiState = loginUiState.copy(signUpErrorConfirPassword = e.localizedMessage)
            }

            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()


        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }


    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)

            repository.loginUser(
                loginUiState.userName.trim(),
                loginUiState.password.trim()
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Hi, Welcome",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Your email or password is wrong",
                        Toast.LENGTH_LONG
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }

            }


        } catch (e: Exception) {
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }


    }

    fun SingOut() = repository.SingOut()

    fun SingInGoogleFirebase(
        credential: AuthCredential,
        context: Context,
        OnNavToHome:()->Unit) = viewModelScope.launch{
        try {
            loginUiState = loginUiState.copy(isLoading = true)
            repository.SingGoogle(credential){
                if(it){
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()

                    loginUiState = loginUiState.copy(isLoading = false)
                    OnNavToHome.invoke()

                }else{
                    loginUiState = loginUiState.copy(isLoading = false)
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }catch (E:Exception){
            Toast.makeText(
                context,
                "${E.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun GetDataUser() {

        viewModelScope.launch {
            try {
                repository.GetDataUserFromFirebase().addOnSuccessListener {
                    println("las stars son ${it.get("stars")}")
                //    stars.value = it.get("stars")
                }
            }catch (e:Exception){
                println(e.message)
            }
        }



    }

    fun sendPasswordResetEmail(context: Context){
        viewModelScope.launch {
            try {
                repository.sendPasswordResetEmail {isSuccessful->

                    if(isSuccessful){
                        Toast.makeText(
                            context,
                            "UnSuccessfull",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            context,
                            "UnSuccessfull",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }catch (e:Exception){

            }
        }
    }
}

