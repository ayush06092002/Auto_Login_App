package com.example.autologin.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.autologin.components.InputField
import com.example.autologin.navigation.LoginScreens

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController, modifier: Modifier = Modifier,
                onLogin: (String) -> Unit = {}) {
    var username = remember {
        mutableStateOf("")
    }
    Scaffold (
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFffc857)),
                title = {
                    Text(text = "Login")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                valueState = username,
                labelId = "Enter Username",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    onLogin(username.value.trim())
                }
            )
            Button(
                onClick = {
                    navController.navigate(LoginScreens.DataScreen.name)
                }
            ) {
                Text(text = "Save Data")
            }
        }
    }
}
