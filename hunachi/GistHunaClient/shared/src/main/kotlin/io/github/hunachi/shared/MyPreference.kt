package io.github.hunachi.shared

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private val NAME_STORE = "Store"
private val NAME_TOKEN = "Token"
private val NAME_FIRST_USER = "isFirstUser"

fun setupSharedPreference(application: Application): SharedPreferences =
        application.getSharedPreferences(NAME_STORE, Context.MODE_PRIVATE)

fun SharedPreferences.savedToken(): String? = getString(NAME_TOKEN, null)

fun SharedPreferences.saveToken(token: String) = edit { putString(NAME_TOKEN, token) }

fun SharedPreferences.isFirstUser(): Boolean = getBoolean(NAME_FIRST_USER, true)

fun SharedPreferences.firstCheckIn() = edit { putBoolean(NAME_FIRST_USER, false) }

