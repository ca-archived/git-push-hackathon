package io.github.hunachi.shared

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val NAME_STORE = "Store"
private const val NAME_TOKEN = "Token"
private const val NAME_FIRST_USER = "isFirstUser"
private const val NAME_OWNER = "ownerName"

fun setupSharedPreference(application: Application): SharedPreferences =
        application.getSharedPreferences(NAME_STORE, Context.MODE_PRIVATE)

fun SharedPreferences.token(): String? = getString(NAME_TOKEN, null)

fun SharedPreferences.token(token: String) = edit { putString(NAME_TOKEN, token) }

fun SharedPreferences.isFirstUser(): Boolean = getBoolean(NAME_FIRST_USER, true)

fun SharedPreferences.firstCheckIn() = edit { putBoolean(NAME_FIRST_USER, false) }

fun SharedPreferences.ownerName() = getString(NAME_OWNER, null)

fun SharedPreferences.ownerName(name: String) = edit { putString(NAME_OWNER, name) }