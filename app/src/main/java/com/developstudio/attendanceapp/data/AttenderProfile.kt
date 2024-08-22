package com.developstudio.attendanceapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttenderProfile(
    val examName: String = "",
    val shiftTime: String = "",
    val centerCode: String = "",
    val password: String = "",
    val name: String = "",
    val profilePicture: String = "",
    val phoneNumber: String = "",
    val documentUploaded: String = "",
    val attenderID: String="",
) : Parcelable

