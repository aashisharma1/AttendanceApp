package com.developstudio.attendanceapp.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

object ShowDialog {

    fun showSnackbar( view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()


    }


}