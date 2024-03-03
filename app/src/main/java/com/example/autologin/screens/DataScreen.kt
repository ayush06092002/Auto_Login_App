package com.example.autologin.screens

import android.annotation.SuppressLint
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.autologin.sharedPrefs.SharedPreferencesHelper

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "QueryPermissionsNeeded",
    "SetJavaScriptEnabled"
)
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
                    Text(text = "Auto Login Page (Press Back to Clear Credentials)", style = TextStyle(color = Color.Black))
                },
                navigationIcon = {
                    // This is the navigation icon for the top app bar
                    IconButton(onClick = { SharedPreferencesHelper.clearCredentials(context)
                        navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        }
    ) {
        // Define the JavaScript code as a global variable
        val jsCode = """
        (function() {
            var data = ${savedUsername?.let { "{'username': '$it', 'password': '${savedPassword ?: ""}'}" } ?: "null"};
            if (data && data.username && data.password) {
                var usernameField = document.getElementById("ft_un");
                var passwordField = document.getElementById("ft_pd");
                if (usernameField && passwordField) {
                    usernameField.value = data.username;
                    passwordField.value = data.password;
                    var submitButton = document.querySelector("button[type='submit']");
                    if (submitButton) {
                        submitButton.click();
                    }
                }
            }
        })();
    """.trimIndent()
        Card(modifier = Modifier.fillMaxSize()
            .background(Color.White)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                AndroidView(factory = { context ->
                    WebView(context).apply {
                        // Load desired webpage URL
                        loadUrl("https://www.google.co.in")

                        // Handle SSL errors
                        // Inside your WebViewClient override
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                val url = request?.url.toString()
                                // Load URL in the WebView
                                view?.loadUrl(url)
                                return true // Indicate that the request has been handled
                            }
                            @SuppressLint("WebViewClientOnReceivedSslError")
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                // Proceed with loading despite SSL error
                                Log.d("SSLScript", "Loaded")
                                // Inject the JavaScript code into the WebView
                                view?.evaluateJavascript(jsCode) { result ->
                                    // Check for errors during JavaScript evaluation
                                    if (result != null) {
                                        Log.d("JavaScriptEvaluation", result)
                                    }
                                }
                            }

                            @SuppressLint("WebViewClientOnReceivedSslError")
                            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                                // Proceed with loading despite SSL error
                                handler?.proceed()
                            }
                        }

                        // Enable JavaScript in WebView settings
                        settings.javaScriptEnabled = true
                    }
                }, modifier = Modifier.height(500.dp))
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
}
