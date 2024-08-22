package com.developstudio.attendanceapp.utils

object localData {

    // Method to return center name based on centerCode
    fun centers(centerCode: String): String {
        return when (centerCode) {
            "1" -> "New Delhi, India"
            "2" -> "Mumbai, India"
            "3" -> "Bangalore, India"
            else -> "Unknown Center"
        }
    }
}