package com.developstudio.attendanceapp.data

data class CandidateDetails(
    val name: String = "",
    val fatherName: String = "",
    val rollNumber: String = "",
    val dob: String = "",
    val shift: String = "",
    val shiftDate: String = "",
    val examName: String = "",
    val isPresent: Boolean = false
)