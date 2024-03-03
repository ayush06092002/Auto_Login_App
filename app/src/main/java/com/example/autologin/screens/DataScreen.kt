package com.example.autologin.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.autologin.sharedPrefs.SharedPreferencesHelper

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "QueryPermissionsNeeded")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DataScreen(navController: NavHostController) {
    val context = LocalContext.current
    val savedPassword = SharedPreferencesHelper.getSavedPassword(context)
    val savedUsername = SharedPreferencesHelper.getSavedUsername(context)
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFffc857)),
                title = {
                    Text(text = "Data")
                },
                navigationIcon = {
                    // This is the navigation icon for the top app bar
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(factory = { context ->
                WebView(context).apply {
                    // Load desired webpage URL
//                    loadUrl("https://192.168.55.253:1003/keepalive?0f08050c0f0d0b04")

                    loadUrl("https://www.google.com")
                    // Handle SSL errors
                    webViewClient = object : WebViewClient() {
                        @SuppressLint("WebViewClientOnReceivedSslError")
                        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                            // Proceed with loading despite SSL error
                            handler?.proceed()

                        }
                    }
                }
            })
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Username: $savedUsername")
            Text(text = "Password: $savedPassword")
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = { SharedPreferencesHelper.clearCredentials(context)
                navController.popBackStack() }) {
                Text(text = "Clear credentials")
            }
        }
    }
}
