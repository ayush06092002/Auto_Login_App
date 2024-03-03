package com.example.autologin.sharedPrefs

import android.content.Context

object SharedPreferencesHelper {
    private const val PREFS_FILENAME = "my_prefs_file"
    private const val USERNAME_KEY = "username"
    private const val PASSWORD_KEY = "password"

    fun saveCredentials(context: Context, username: String, password: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString(USERNAME_KEY, username)
            putString(PASSWORD_KEY, password)
            apply()
        }
    }

    fun getSavedUsername(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USERNAME_KEY, null)
    }

    fun getSavedPassword(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PASSWORD_KEY, null)
    }

    fun clearCredentials(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}
