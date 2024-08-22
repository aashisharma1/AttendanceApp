package com.developstudio.attendanceapp.utils

import android.content.Context

object UserIdManager {

    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"

    // Method to save the userId in SharedPreferences
    fun saveUserId(context: Context, userId: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    // Method to get the userId from SharedPreferences
    fun getUserId(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USER_ID, null)
    }
}