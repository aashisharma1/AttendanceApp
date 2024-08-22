package com.developstudio.attendanceapp.utils

import android.content.Context
import androidx.biometric.BiometricManager

object BiometricHardware {
    fun isBiometricHardwareAvailable(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true  // Biometric hardware is available and can be used
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> false // No biometric hardware available
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> false // Biometric hardware is currently unavailable
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> false // No biometrics enrolled on the device
            else -> false
        }
    }

}