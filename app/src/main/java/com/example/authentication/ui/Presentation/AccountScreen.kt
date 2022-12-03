package com.example.authentication.ui.Presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.authentication.ui.Foundation.Account.HListWord
import com.example.authentication.ui.Foundation.Account.HeaderAccount
import com.example.authentication.ui.Foundation.Account.SubTitles
import com.example.authentication.ui.Presentation.Login.LineColor
import com.example.authentication.ui.ViewModels.LoginViewModel


//Id - loginViewModel.userId
//
@Composable
fun AccountScreen(
    loginViewModel: LoginViewModel,
    onNavToLogin: () -> Unit
) {

    loginViewModel.GetDataUser()

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        item {
            HeaderAccount(
                UserName =loginViewModel.username,
                UserEmail = loginViewModel.useremail,
                UserPhoto = loginViewModel.userphoto ,
                Stars = loginViewModel.stars,
                Level = 0,
                Exp = 0)
            LineColor(height = 10f)
            SubTitles("My favorites words")
            HListWord()
            LineColor(height = 5f)
            SubTitles("My favorites Sentes")
            HListWord()
            LineColor(height = 5f)
            SubTitles("My favorites Gramar")
            HListWord()

            Button(onClick = {
                loginViewModel.SingOut()
                onNavToLogin.invoke()
            }) {
                Text(text = "cerrar sesion")

            }

        }
    }

}