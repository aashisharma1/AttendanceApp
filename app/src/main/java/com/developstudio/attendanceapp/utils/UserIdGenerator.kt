package com.developstudio.attendanceapp.utils

object UserIdGenerator {

    // Method to generate and return a user ID
    fun generateUserId(): String {
        // Generate a random user ID, here using a random number for simplicity
        val randomId = (100000000000..999999999999).random().toString()
        return randomId
    }
}