package com.example.autologin.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.autologin.components.InputField
import com.example.autologin.navigation.LoginScreens
import com.example.autologin.sharedPrefs.SharedPreferencesHelper

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController, modifier: Modifier = Modifier,
                onLogin: (String) -> Unit = {}) {
    var username = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    // Check if credentials are already saved
    val savedUsername = SharedPreferencesHelper.getSavedUsername(context)
    val savedPassword = SharedPreferencesHelper.getSavedPassword(context)

    if (!savedUsername.isNullOrBlank() && !savedPassword.isNullOrBlank()) {
        // If credentials are saved, navigate to DataScreen
        LaunchedEffect(Unit) {
            navController.navigate(LoginScreens.DataScreen.name)
        }
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
            Spacer(modifier = Modifier.size(10.dp))
            InputField(
                valueState = password,
                labelId = "Enter Password",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    onLogin(password.value.trim())
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = {
                    if(username.value.isEmpty() || password.value.isEmpty()){
                        return@Button
                    }
                    SharedPreferencesHelper.saveCredentials(context, username.value.trim(), password.value.trim())
                    navController.navigate(LoginScreens.DataScreen.name)
                }
            ) {
                Text(text = "Save Data")
            }
        }
    }
}
