package com.developstudio.attendanceapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.developstudio.attendanceapp.R
import com.developstudio.attendanceapp.databinding.FragmentMainScreenBinding
import com.developstudio.attendanceapp.utils.BiometricHardware
import com.developstudio.attendanceapp.utils.ShowDialog
import com.developstudio.attendanceapp.utils.UserIdManager

class MainScreen : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    /*
    This is our main Screen
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        requireActivity().enableEdgeToEdge()
        requireActivity().actionBar?.hide()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BiometricHardware.isBiometricHardwareAvailable(requireContext())

        // Checking if Scanner is present or not
        if (BiometricHardware.isBiometricHardwareAvailable(requireContext())) {
            ShowDialog.showSnackbar(binding.cardVerification, "FingerPrint Scanner Detected")
        } else {
            ShowDialog.showSnackbar(binding.cardVerification, "FingerPrint Scanner Not Detected")
        }

        binding.cardVerification.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen2_to_verificationScreen2)
        }

        if (!UserIdManager.getUserId(requireContext()).isNullOrEmpty()) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}